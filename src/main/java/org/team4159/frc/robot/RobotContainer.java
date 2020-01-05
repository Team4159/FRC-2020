package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.traj.TrajectoryFromTrenchRunToShootingPosition;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();

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
            TrajectoryFromTrenchRunToShootingPosition.generateTrajectory(),
            drivetrain::getPose,
            new RamseteController(),
            new SimpleMotorFeedforward(1, 0, 0),
            new DifferentialDriveKinematics(1),
            drivetrain::getWheelSpeeds,
            new PIDController(1, 0, 0),
            new PIDController(1, 0, 0),
            drivetrain::voltsDrive,
            drivetrain
    );

    return ramsete_command;
  }
}
