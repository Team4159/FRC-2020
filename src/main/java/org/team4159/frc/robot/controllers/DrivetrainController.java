package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.control.signal.DriveSignal;

public class DrivetrainController implements ControlLoop {
  private enum State {
    PATH_FOLLOWING,
    OPEN_LOOP
  }
  private State state = State.OPEN_LOOP;

  private boolean is_drive_oriented_forwards = true;
  private DriveSignal demanded_signal = DriveSignal.NEUTRAL;

  private Drivetrain drivetrain;
  private TrajectoryController trajectory_controller;

  public DrivetrainController(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    this.trajectory_controller = new TrajectoryController(drivetrain);
  }

  @Override
  public void update() {
    DriveSignal filtered_signal = DriveSignal.NEUTRAL;

    switch (state) {
      case OPEN_LOOP:
        if (is_drive_oriented_forwards) {
          filtered_signal = demanded_signal;
        } else {
          filtered_signal = demanded_signal.invert();
        }
        break;
      case PATH_FOLLOWING:
        trajectory_controller.update();
        if (trajectory_controller.isComplete()) {
          state = State.OPEN_LOOP;
        }
        break;
    }

    drivetrain.drive(filtered_signal);
  }

  public void demandSignal(DriveSignal signal) {
    demanded_signal = signal;
  }

  public void flipDriveOrientation() {
    is_drive_oriented_forwards = !is_drive_oriented_forwards;
  }

  public void startFollowingTrajectory(Trajectory trajectory) {
    state = State.PATH_FOLLOWING;
    trajectory_controller.setTrajectory(trajectory);
  }

  public void cancelPathFollowing() {
    state = State.OPEN_LOOP;
  }
}
