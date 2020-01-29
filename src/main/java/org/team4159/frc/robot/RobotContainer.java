package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

import org.team4159.frc.robot.commands.turret.LimelightSeek;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Turret turret = new Turret();

  private final Limelight limelight = new Limelight();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

 private final AutoSelector auto_selector = new AutoSelector(drivetrain);

  public RobotContainer() {
    configureButtonBindings();

    SmartDashboard.putData("Autonomous", auto_selector);
  }

  private void configureButtonBindings() {
    new JoystickButton(left_joy, 1)
      .whenPressed(drivetrain::flipOrientation);

    new JoystickButton(left_joy, 2)
      .whenHeld(new LimelightSeek(turret, limelight));
  }

  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
