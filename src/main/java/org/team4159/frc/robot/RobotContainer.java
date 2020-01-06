package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.traj.Trajectories;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  public final Drivetrain drivetrain = new Drivetrain();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

  public RobotContainer() {
    drivetrain.setDefaultCommand(
            new RunCommand(() -> drivetrain.rawDrive(
                    left_joy.getY(),
                    right_joy.getY()
            ), drivetrain));
  }

  public Command getAutonomousCommand() {
    RamseteCommand ramsete_command = new RamseteCommand(
            Trajectories.testTrajectory(), // desired trajectory to follow
            drivetrain::getPose, // method reference (lambda) to pose supplier
            DRIVE_CONSTANTS.kDriveRamseteController, // Empty constructor means default gains
            DRIVE_CONSTANTS.kDriveFeedforwardGains, // Feedforward gains kS, kV, kA obtained from characterization
            DRIVE_CONSTANTS.kDriveKinematics, // track width
            drivetrain::getWheelSpeeds, // lambda to wheel speed supplier
            DRIVE_CONSTANTS.kDrivePController, // left side PID using Proportional gain from characterization
            DRIVE_CONSTANTS.kDrivePController, // right side PID using Proportional gain from characterization
            drivetrain::voltsDrive, // lambda to pass voltage outputs to motors
            drivetrain // require drivetrain subsystem
    );

    return ramsete_command.andThen(() -> drivetrain.rawDrive(0.0, 0.0));
  }
}
