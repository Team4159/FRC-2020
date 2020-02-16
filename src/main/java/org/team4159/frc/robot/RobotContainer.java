package org.team4159.frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.commands.arm.ToggleArm;
import org.team4159.frc.robot.commands.arm.ZeroArm;
import org.team4159.frc.robot.commands.turret.LimelightSeek;
import org.team4159.frc.robot.commands.turret.ZeroTurret;
import org.team4159.frc.robot.subsystems.*;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Limelight limelight = new Limelight();
  private final Shooter shooter = new Shooter(limelight);
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Neck neck = new Neck();
  private final Arm arm = new Arm();
  private final Turret turret = new Turret(limelight);

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY.USB_PORT);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY.USB_PORT);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY.USB_PORT);

 private final AutoSelector auto_selector = new AutoSelector(drivetrain);

  public RobotContainer() {
    configureCamera();
    configureCommands();
    configureButtonBindings();
  }

  private void configureCamera() {
    CameraServer.getInstance().startAutomaticCapture();
  }

  private void configureCommands() {
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.tankDrive(
        left_joy.getY(),
        right_joy.getY()
      ),
      drivetrain
    ));

    turret.setDefaultCommand(
      new RunCommand(
        () -> turret.setRawSpeed(
          secondary_joy.getY() / 3.0
        ),
        turret
    ));

    new ZeroArm(arm).schedule(false);
    new ZeroTurret(turret).schedule(false);
  }

  private void configureButtonBindings() {
    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.ENABLE_SHOOTER)
      .whenPressed(() -> shooter.setTargetSpeed(SmartDashboard.getNumber("target_shooter_speed", 0)))
      .whenReleased(new InstantCommand(shooter::stop, shooter));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION)
      .whenPressed(drivetrain::flipOrientation);

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_INTAKE)
      .whenPressed(new InstantCommand(intake::intake, intake))
      .whenReleased(new InstantCommand(intake::stop, intake));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.TOGGLE_ARM)
      .whenPressed(new ToggleArm(arm));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_FEEDER)
      .whenPressed(new InstantCommand(feeder::feed, feeder))
      .whenReleased(new InstantCommand(feeder::stop, feeder));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_ALL_INTAKE_SUBSYSTEMS)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(intake::intake, intake),
        new InstantCommand(feeder::feed, feeder),
        new InstantCommand(() -> shooter.setRawSpeed(1), shooter)))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(intake::stop, intake),
        new InstantCommand(feeder::stop, feeder),
        new InstantCommand(() -> shooter.setRawSpeed(0), shooter)));
  }

  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
