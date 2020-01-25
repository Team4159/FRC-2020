package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;

import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.Shooter;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

  public RobotContainer() {
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.setRawSpeeds(
        left_joy.getY(),
        right_joy.getY()
      ),
      drivetrain
    ));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new JoystickButton(left_joy, 1)
      .whenPressed(new ConditionalCommand(
        new InstantCommand(
          shooter::enable,
          shooter
        ),
        new InstantCommand(
          shooter::disable,
          shooter
        ),
        shooter::isEnabled
      ));
  }

  public Shooter getShooter() {
    return shooter;
  }
}
