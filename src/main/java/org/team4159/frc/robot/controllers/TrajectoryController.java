package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.control.signal.DriveSignal;

public class TrajectoryController implements ControlLoop {
  private Drivetrain drivetrain;

  private Trajectory trajectory_to_follow;

  private DifferentialDriveWheelSpeeds prev_speeds = new DifferentialDriveWheelSpeeds(0,0);
  private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.DRIVE_CONSTANTS.TRACK_WIDTH);
  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(Constants.DRIVE_CONSTANTS.kS, Constants.DRIVE_CONSTANTS.kV, Constants.DRIVE_CONSTANTS.kA);
  private RamseteController controller = new RamseteController(Constants.DRIVE_CONSTANTS.kB, Constants.DRIVE_CONSTANTS.kZeta);
  private PIDController pid_left = new PIDController(Constants.DRIVE_CONSTANTS.kP, 0, Constants.DRIVE_CONSTANTS.kD);
  private PIDController pid_right = new PIDController(Constants.DRIVE_CONSTANTS.kP, 0, Constants.DRIVE_CONSTANTS.kD);

  private Timer timer = new Timer();
  private double prev_time = 0;

  public TrajectoryController(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void update() {
    double cur_time = timer.get();
    double dt = cur_time - prev_time;

    Pose2d drivetrain_pose = drivetrain.getPose();
    Trajectory.State trajectory_sample = trajectory_to_follow.sample(cur_time);

    var target_wheel_speeds = kinematics.toWheelSpeeds(
      controller.calculate(drivetrain_pose, trajectory_sample));

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

    prev_time = cur_time;
    prev_speeds = target_wheel_speeds;

    DriveSignal signal = DriveSignal.fromVolts(left_output, right_output);
    drivetrain.drive(signal);
  }

  public boolean isComplete() {
    return timer.hasPeriodPassed(trajectory_to_follow.getTotalTimeSeconds());
  }

  public void setTrajectory(Trajectory trajectory) {
    Transform2d transform = drivetrain.getPose().minus(trajectory.getInitialPose());
    trajectory = trajectory.transformBy(transform);
    trajectory_to_follow = trajectory;

    Trajectory.State initialState = trajectory_to_follow.sample(0);
    prev_speeds = kinematics.toWheelSpeeds(
      new ChassisSpeeds(initialState.velocityMetersPerSecond,
        0,
        initialState.curvatureRadPerMeter
          * initialState.velocityMetersPerSecond));
    prev_time = 0;
    timer.reset();
    timer.start();
  }
}
