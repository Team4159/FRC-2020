package org.team4159.frc.robot.commands.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.CsvWriter;

import static org.team4159.frc.robot.Constants.*;

public class FollowTrajectory extends CommandBase {
  private RamseteCommand ramsete;
  private Trajectory traj;
  private Drivetrain drivetrain;

  private Timer timer = new Timer();

  private CsvWriter writer;

  private DifferentialDriveWheelSpeeds prev_speeds = new DifferentialDriveWheelSpeeds(0,0);
  private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH);
  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DRIVE_CONSTANTS.kS, DRIVE_CONSTANTS.kV, DRIVE_CONSTANTS.kA);
  private RamseteController controller = new RamseteController(DRIVE_CONSTANTS.kB, DRIVE_CONSTANTS.kZeta);
  private PIDController pid_left = new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD);
  private PIDController pid_right = new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD);
  double prev_time = 0;

  public FollowTrajectory(Trajectory trajectory, Drivetrain drivetrain) {
    addRequirements(drivetrain);
    this.drivetrain = drivetrain;

    var transform = drivetrain.getPose().minus(trajectory.getInitialPose());
    traj = trajectory.transformBy(transform);

    ramsete = new RamseteCommand(
      trajectory,
      drivetrain::getPose,
      new RamseteController(DRIVE_CONSTANTS.kB,
        DRIVE_CONSTANTS.kZeta),
      new SimpleMotorFeedforward(DRIVE_CONSTANTS.kS,
        DRIVE_CONSTANTS.kV,
        DRIVE_CONSTANTS.kA),
      new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH),
      drivetrain::getWheelSpeeds,
      new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD),
      new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD),
      drivetrain::voltsDrive,
      drivetrain
    );

//    addCommands(
//      ramsete,
//      new InstantCommand(drivetrain::stop)
//    );
  }

  @Override
  public void initialize() {
    writer = new CsvWriter("/home/lvuser/Output");

    prev_time = 0;
    var initialState = traj.sample(0);
    prev_speeds = kinematics.toWheelSpeeds(
      new ChassisSpeeds(initialState.velocityMetersPerSecond,
        0,
        initialState.curvatureRadPerMeter
          * initialState.velocityMetersPerSecond));
    timer.reset();
    timer.start();
  }

  @Override
  public void execute() {
    double cur_time = timer.get();
    double dt = cur_time - prev_time;

    var target_wheel_speeds = kinematics.toWheelSpeeds(
      controller.calculate(drivetrain.getPose(), traj.sample(cur_time)));

    double left_speed_setpoint = target_wheel_speeds.leftMetersPerSecond;
    double right_speed_setpoint = target_wheel_speeds.rightMetersPerSecond;

    double left_feed_forward =
      feedforward.calculate(left_speed_setpoint,
        (left_speed_setpoint - prev_speeds.leftMetersPerSecond) / dt);
    double right_feed_forward =
      feedforward.calculate(right_speed_setpoint,
        (right_speed_setpoint - prev_speeds.rightMetersPerSecond) / dt);

    double left_PID = pid_left.calculate(drivetrain.getWheelSpeeds().leftMetersPerSecond, left_speed_setpoint);
    double right_PID = pid_right.calculate(drivetrain.getWheelSpeeds().rightMetersPerSecond, right_speed_setpoint);

    double left_output = left_feed_forward + left_PID;

    double right_output = right_feed_forward + right_PID;

    SmartDashboard.putNumber("target left wheel speeds", target_wheel_speeds.leftMetersPerSecond);
    SmartDashboard.putNumber("target right wheel speeds", target_wheel_speeds.rightMetersPerSecond);

    SmartDashboard.putNumber("left feed forward", left_feed_forward);
    SmartDashboard.putNumber("right feed forward", right_feed_forward);

    SmartDashboard.putNumber("left pid", left_PID);
    SmartDashboard.putNumber("right pid", right_PID);

    SmartDashboard.putNumber("left output", left_output);
    SmartDashboard.putNumber("right output", right_output);

    final Pose2d drivetrainPose = drivetrain.getPose();
    final Pose2d trajSample = traj.sample(cur_time).poseMeters;

    writer.write(
      target_wheel_speeds.leftMetersPerSecond, // 1
      target_wheel_speeds.rightMetersPerSecond, // 2
      left_feed_forward, // 3
      right_feed_forward, // 4
      left_PID, // 5
      right_PID, // 6
      left_output, // 7
      right_output, // 8
      drivetrainPose.getTranslation().getX(), //9
      drivetrainPose.getTranslation().getY(), // 10
      trajSample.getTranslation().getX(), // 11
      trajSample.getTranslation().getY(), // 12
      drivetrainPose.getRotation().getDegrees(), // 13
      trajSample.getRotation().getDegrees() // 14
    );

    prev_time = cur_time;
    prev_speeds = target_wheel_speeds;

    drivetrain.voltsDrive(left_output, right_output);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("DONE!");
    System.out.println(writer.close());

    drivetrain.stop();
  }

  @Override
  public boolean isFinished() {
      return timer.hasPeriodPassed(traj.getTotalTimeSeconds());
    }

}
