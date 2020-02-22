package org.team4159.frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.lib.hardware.Limelight;
import org.team4159.frc.robot.controllers.complex.IntakeController;
import org.team4159.frc.robot.subsystems.*;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Neck neck = new Neck();
  private final Arm arm = new Arm();
  private final Turret turret = new Turret();

  private final IntakeController intake_controller = new IntakeController(arm.getController(), intake, feeder);

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY.USB_PORT);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY.USB_PORT);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY.USB_PORT);

 private final AutoSelector auto_selector = new AutoSelector();

  public RobotContainer() {
    configureCamera();
  }

  private void configureCamera() {
    CameraServer.getInstance().startAutomaticCapture();
  }

  private void configureButtonBindings() {
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

  public void updateSubsystemInputs() {
    updateDrivetrainInputs();
    updateFeederInputs();

    // TODO: switch to IntakeController when done testing in isolation
    updateArmInputs();
    updateIntakeInputs();

    updateNeckInputs();
  }

  public void updateControllerInputs() {
    updateIntakeControllerInputs();
  }

  public void updateArmInputs() {
    if (secondary_joy.getRawButtonPressed(CONTROLS.SECONDARY_JOY.BUTTON_IDS.TOGGLE_ARM)) {
      arm.getController().setSetpoint(Math.abs(arm.getController().getSetpoint() - ARM_CONSTANTS.RANGE_IN_COUNTS));
    }
  }

  public void updateDrivetrainInputs() {
    if (secondary_joy.getRawButtonPressed(CONTROLS.SECONDARY_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION)) {
      drivetrain.flipOrientation();
    }

    drivetrain.drive(new DriveSignal(left_joy.getY(), right_joy.getY()));
  }

  public void updateFeederInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_FEEDER)) {
      feeder.feed();
    } else {
      feeder.stop();
    }
  }

  public void updateIntakeInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_INTAKE)) {
      intake.intake();
    } else {
      intake.stop();
    }
  }

  public void updateNeckInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_NECK)) {
      neck.neck();
    } else {
      neck.stop();
    }
  }

  public void updateIntakeControllerInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_INTAKE)) {
      intake_controller.intake();
    } else {
      intake_controller.stopIntaking();
    }
  }
}
