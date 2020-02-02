package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.commands.arm.*;
import org.team4159.frc.robot.subsystems.*;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Neck neck = new Neck();
  private final Arm arm = new Arm();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY);

  private final AutoSelector auto_selector = new AutoSelector(drivetrain);

  public RobotContainer() {
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.tankDrive(
        left_joy.getY(),
        right_joy.getY()
      ),
      drivetrain
    ));

    arm.setDefaultCommand(new RunCommand(() -> arm.setRawSpeed(secondary_joy.getY()), arm));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new JoystickButton(secondary_joy, 1)
      .whenPressed(new ConditionalCommand(
        new InstantCommand(shooter::enable, shooter),
        new InstantCommand(shooter::disable, shooter),
        shooter::isEnabled
      ));

    new JoystickButton(secondary_joy, 2)
      .whenPressed(drivetrain::flipOrientation);

    new JoystickButton(secondary_joy, 3)
      .whenPressed(new InstantCommand(intake::intakeCell, intake))
      .whenReleased(new InstantCommand(intake::stopIntaking, intake));

    new JoystickButton(secondary_joy, 4)
      .whenPressed(new ToggleArm(arm));

    new JoystickButton(secondary_joy, 5)
      .whenPressed(new InstantCommand(neck::neck))
      .whenReleased(new InstantCommand(neck::stop));

    new JoystickButton(secondary_joy, 6)
      .whenPressed(new InstantCommand(feeder::feed, feeder))
      .whenReleased(new InstantCommand(feeder::stop, feeder));

    new JoystickButton(secondary_joy, 7)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(intake::intakeCell, intake),
        new InstantCommand(feeder::feed, feeder),
        new InstantCommand(neck::neck),
        new InstantCommand(() -> shooter.setRawSpeed(1))))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(intake::stopIntaking, intake),
        new InstantCommand(feeder::stop, feeder),
        new InstantCommand(neck::stop),
        new InstantCommand(() -> shooter.setRawSpeed(0))));
  }


  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
