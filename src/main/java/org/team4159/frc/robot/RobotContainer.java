package org.team4159.frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.frc.robot.controllers.complex.IntakeController;
import org.team4159.frc.robot.subsystems.*;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Neck neck = new Neck();
  private final Arm arm = new Arm();
  private final Turret turret = new Turret();

  private final Limelight limelight = Limelight.getDefault();

  private final IntakeController intake_controller = new IntakeController(arm.getController(), intake, feeder);

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY.USB_PORT);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY.USB_PORT);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY.USB_PORT);

  private final AutoSelector auto_selector = new AutoSelector();

  public RobotContainer() {
    configureCameras();
    configureButtonBindings();
  }

  private void configureCameras() {
    //CameraServer.getInstance().startAutomaticCapture();
    limelight.setLEDMode(Limelight.LEDMode.ForceOff);
  }

  public void zeroSubsystems() {
    arm.getController().startZeroing();
    turret.getController().startZeroing();
  }

  public void configureButtonBindings() {
    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.TOGGLE_ARM).whenPressed(
      new ConditionalCommand(
        new InstantCommand(() -> arm.getController().setSetpoint(ARM_CONSTANTS.DOWN_POSITION)),
        new InstantCommand(() -> arm.getController().setSetpoint(ARM_CONSTANTS.UP_POSITION)),
        () -> arm.getController().getSetpoint() == ARM_CONSTANTS.UP_POSITION)
    );

    new JoystickButton(secondary_joy, CONTROLS.LEFT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION).whenPressed(
      new InstantCommand(() -> {
        drivetrain.getController().flipDriveOrientation();
        drivetrain.getController().demandSignal(new DriveSignal(left_joy.getY(), right_joy.getY()));
      })
    );

    new JoystickButton(secondary_joy, CONTROLS.RIGHT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION).whenPressed(
      new InstantCommand(() -> {
        drivetrain.getController().flipDriveOrientation();
        drivetrain.getController().demandSignal(new DriveSignal(left_joy.getY(), right_joy.getY()));
      })
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_FEEDER).whenPressed(
      new InstantCommand(feeder::feed)
    ).whenReleased(
      new InstantCommand(feeder::stop)
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_INTAKE).whenPressed(
      new InstantCommand(intake::intake)
    ).whenReleased(
      new InstantCommand(intake::stop)
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_INTAKE).whenPressed(
      new InstantCommand(neck::neck)
    ).whenReleased(
      new InstantCommand(neck::stop)
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_SHOOTER).whenPressed(
      new InstantCommand(() -> {
        shooter.getController().setTargetSpeed(SHOOTER_CONSTANTS.MAX_SPEED);
        shooter.getController().spinUp();
      })
    ).whenReleased(
      new InstantCommand(() -> shooter.getController().spinDown())
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.LIMELIGHT_SEEK).whenPressed(
      new InstantCommand(() -> turret.getController().startSeeking())
    ).whenReleased(
      new InstantCommand(() -> turret.getController().idle())
    );

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.BUTTON_IDS.INTAKE).whenPressed(
      new InstantCommand(intake_controller::intake)
    ).whenReleased(
      new InstantCommand(intake_controller::stopIntaking)
    );
  }
}
