package org.team4159.frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;

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
  }

  private void configureCameras() {
    //CameraServer.getInstance().startAutomaticCapture();
    limelight.setLEDMode(Limelight.LEDMode.ForceOff);
  }

  public void zeroSubsystems() {
    if (Robot.isReal()) {
      arm.getController().startZeroing();
      turret.getController().startZeroing();
    }
  }

  public void updateSubsystemInputs() {
    if (Robot.isReal()) {
      updateArmInputs();
      updateIntakeInputs();
      updateFeederInputs();

      updateNeckInputs();
      updateShooterInputs();
      updateTurretInputs();
    }
  }

  public void updateControllerInputs() {
    updateDrivetrainControllerInputs();
    // updateIntakeControllerInputs();
  }

  public void updateArmInputs() {
    if (secondary_joy.getRawButtonPressed(CONTROLS.SECONDARY_JOY.BUTTON_IDS.TOGGLE_ARM)) {
      if (arm.getController().getSetpoint() == ARM_CONSTANTS.UP_POSITION) {
        arm.getController().setSetpoint(ARM_CONSTANTS.DOWN_POSITION);
      } else {
        arm.getController().setSetpoint(ARM_CONSTANTS.UP_POSITION);
      }
    }
  }

  public void updateDrivetrainControllerInputs() {
    if (left_joy.getRawButtonPressed(CONTROLS.LEFT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION) ||
      right_joy.getRawButtonPressed(CONTROLS.RIGHT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION)) {
      drivetrain.getController().flipDriveOrientation();
    }

    //drivetrain.getController().demandSignal(new DriveSignal(left_joy.getY(), right_joy.getY()));
    drivetrain.getController().demandSignal(new DriveSignal(1.0, 1.0));
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

  public void updateShooterInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_SHOOTER)) {
      shooter.getController().setTargetSpeed(SHOOTER_CONSTANTS.MAX_SPEED);
      shooter.getController().spinUp();
    } else {
      shooter.getController().spinDown();
    }
  }

  public void updateTurretInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.LIMELIGHT_SEEK)) {
      turret.getController().startSeeking();
    } else {
      turret.getController().idle();
    }
  }

  public void updateIntakeControllerInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.INTAKE)) {
      intake_controller.intake();
    } else {
      intake_controller.stopIntaking();
    }
  }
}