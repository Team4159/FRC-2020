package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import org.team4159.frc.robot.commands.arm.ToggleArm;
import org.team4159.frc.robot.commands.arm.ZeroArm;
import org.team4159.frc.robot.subsystems.*;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
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

    shooter.setDefaultCommand(new RunCommand(
      () -> shooter.setRawSpeed(secondary_joy.getY()),
      shooter
     ));

    //new ZeroArm(arm).schedule(false);

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
      .whenPressed(new InstantCommand(intake::intakeCell, intake))
      .whenReleased(new InstantCommand(intake::stopIntaking, intake));

    new JoystickButton(secondary_joy, 3)
      .whenPressed(new ToggleArm(arm));

    new JoystickButton(secondary_joy, 4)
      .whenPressed(new InstantCommand(neck::neck))
      .whenReleased(new InstantCommand(neck::stop));
  }

  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
