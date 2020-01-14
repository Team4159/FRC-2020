package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import org.team4159.frc.robot.commands.CharacterizationRoutine;
import org.team4159.frc.robot.commands.FollowTrajectory;
import org.team4159.frc.robot.subsystems.Drivetrain;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  public final Drivetrain drivetrain = new Drivetrain();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

  private final FollowTrajectory follow_trajectory_command = new FollowTrajectory(
          TRAJECTORIES.TEST_TRAJECTORY,
          drivetrain,
          drivetrain::getPose,
          drivetrain::getWheelSpeeds,
          drivetrain::voltsDrive);
  private final CharacterizationRoutine characterization_command = new CharacterizationRoutine(
          drivetrain);
  private final InstantCommand default_command = new InstantCommand();

  private SendableChooser<Command> autonomous_chooser;

  public RobotContainer() {
    drivetrain.setDefaultCommand(
            new RunCommand(() -> drivetrain.rawDrive(
                    left_joy.getY(),
                    right_joy.getY()
            ), drivetrain));

    autonomous_chooser = new SendableChooser<>();
    autonomous_chooser.addOption("Follow Trajectory", follow_trajectory_command);
    autonomous_chooser.addOption("Characterization Routine", characterization_command);
    autonomous_chooser.setDefaultOption("No Autonomous", default_command);
  }

  public Command getAutonomousCommand() {
    return autonomous_chooser.getSelected();
  }
}
