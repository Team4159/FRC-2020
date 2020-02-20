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

import org.team4159.frc.robot.subsystems.IDrivetrain;
import org.team4159.lib.control.signal.DriveSignal;

import static org.team4159.frc.robot.Constants.*;

public class DrivetrainController {
  private enum State {
    FOLLOWING,
    IDLE
  }
  private State state = State.IDLE;

  private IDrivetrain drivetrain;

  private Trajectory trajectory_to_follow;
  private DriveSignal demanded_signal;
  private boolean is_oriented_forwards = true;

  private DifferentialDriveWheelSpeeds prev_speeds = new DifferentialDriveWheelSpeeds(0,0);
  private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH);
  private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(DRIVE_CONSTANTS.kS, DRIVE_CONSTANTS.kV, DRIVE_CONSTANTS.kA);
  private RamseteController controller = new RamseteController(DRIVE_CONSTANTS.kB, DRIVE_CONSTANTS.kZeta);
  private PIDController pid_left = new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD);
  private PIDController pid_right = new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD);

  private Timer timer = new Timer();
  private double prev_time = 0;

  public DrivetrainController(IDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  public void update() {
    DriveSignal filtered_signal = DriveSignal.NEUTRAL;

    switch (state) {
      case FOLLOWING:
        if (timer.get() > trajectory_to_follow.getTotalTimeSeconds()) {
          state = State.IDLE;
          break;
        }

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

        filtered_signal = DriveSignal.fromVolts(left_output, right_output);
        break;
      case IDLE:
        filtered_signal = demanded_signal;
        break;
    }

    if (!is_oriented_forwards) {
      filtered_signal = filtered_signal.invert();
    }

    drivetrain.drive(filtered_signal);
  }

  public boolean isIdle() {
    return state == State.IDLE;
  }

  public void setDriveSignal(DriveSignal signal) {
    this.demanded_signal = signal;
  }

  public void flipDriveOrientation() {
    is_oriented_forwards = !is_oriented_forwards;
  }

  public void setTrajectory(Trajectory trajectory) {
    state = State.FOLLOWING;

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
