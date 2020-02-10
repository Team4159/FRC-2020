package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.commands.arm.ToggleArm;
import org.team4159.frc.robot.commands.arm.ZeroArm;
import org.team4159.frc.robot.commands.turret.LimelightSeek;
import org.team4159.lib.hardware.Limelight;
import org.team4159.frc.robot.subsystems.*;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Arm arm = new Arm();
  private final Turret turret = new Turret();

  private final Limelight limelight = new Limelight();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY.PORT);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY.PORT);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY.PORT);

 private final AutoSelector auto_selector = new AutoSelector(drivetrain);

  public RobotContainer() {
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.tankDrive(
        left_joy.getY(),
        right_joy.getY()
      ),
      drivetrain
    ));

    new ZeroArm(arm).schedule(false);

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_SHOOTER_BTN)
      .whenPressed(() -> shooter.setTargetSpeed(SmartDashboard.getNumber("target_shooter_speed", 0)))
      .whenReleased(new InstantCommand(shooter::stop, shooter));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.FLIP_ORIENTATION_BTN_ID)
      .whenPressed(drivetrain::flipOrientation);

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_INTAKE_BTN_ID)
      .whenPressed(new InstantCommand(intake::intake, intake))
      .whenReleased(new InstantCommand(intake::stop, intake));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.TOGGLE_ARM_BTN_ID)
      .whenPressed(new ToggleArm(arm));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_FEEDER_BTN_ID)
      .whenPressed(new InstantCommand(feeder::feed, feeder))
      .whenReleased(new InstantCommand(feeder::stop, feeder));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_INTAKE_NECK_FEEDER_SHOOTER_BTN_ID)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(intake::intake, intake),
        new InstantCommand(feeder::feed, feeder),
        new InstantCommand(() -> shooter.setRawSpeed(1), shooter)))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(intake::stop, intake),
        new InstantCommand(feeder::stop, feeder),
        new InstantCommand(() -> shooter.setRawSpeed(0), shooter)));

    new JoystickButton(left_joy, 1)
      .whenPressed(turret::enable)
      .whenReleased(turret::disable);
  }

  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
