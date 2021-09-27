package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.frc.robot.controllers.complex.IntakeController;
import org.team4159.frc.robot.subsystems.*;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

import org.team4159.frc.robot.Constants.CONTROLS;

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

  public CommandBase getAutoCommand() {
    // TODO: Generalize with auto selector
    // return FollowTrajectory.createCommand(Trajectories.BARREL_TRAJECTORY, drivetrain);
    return auto_selector.getSelected();
  }

  public RobotContainer() {
    Trajectories.loadTrajectories();

    configureCameras();
  }

  private void configureCameras() {
    //CameraServer.getInstance().startAutomaticCapture();
    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
  }

  public void zeroSubsystems() {
    // Arm motor (CAN ID 2) not working

    // ZEROING DISABLED AS PER 3/19 as NO LIMIT SWITCHES EXIST FOR EITHER SUBSYSTEM
    arm.getController().startZeroing();
    turret.getController().startZeroing();
  }

  public void updateSubsystemInputs() {
    // Arm motor (CAN ID 2) not working
    updateIntakeInputs();
    // updateFeederInputs();
    updateNeckInputs();
    updateShooterInputs();
    updateTurretInputs();
  }

  public void updateControllerInputs() {
    // Disabled for testing subsystems
    updateDrivetrainControllerInputs();

    updateArmControllerInputs();
    updateIntakeControllerInputs();
    intake_controller.update();
  }

  /*For future reference:
    new JoystickButton(joystick,1).whileActiveOnce(ArmUpCmd(arm));

  */

  public void updateArmControllerInputs() {
    if (secondary_joy.getRawButtonPressed(CONTROLS.SECONDARY_JOY.BUTTON_IDS.UP_ARM)) {
      if (arm.getController().getSetpoint() != ARM_CONSTANTS.UP_POSITION) {
        arm.getController().setSetpoint(ARM_CONSTANTS.UP_POSITION);
      } 
    }

    if(secondary_joy.getRawButtonPressed(CONTROLS.SECONDARY_JOY.BUTTON_IDS.ARM_DOWN)) {
      if(arm.getController().getSetpoint() != ARM_CONSTANTS.DOWN_POSITION) {
        arm.getController.setSetpoint(ARM_CONSTANTS.DOWN_POSITION);
      }
    }
  }

  public void updateDrivetrainControllerInputs() {
    if (left_joy.getRawButtonPressed(CONTROLS.LEFT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION) ||
      right_joy.getRawButtonPressed(CONTROLS.RIGHT_JOY.BUTTON_IDS.FLIP_ROBOT_ORIENTATION)) {
      drivetrain.getController().flipDriveOrientation();
    }

    drivetrain.getController().demandSignal(new DriveSignal(left_joy.getY(), right_joy.getY()));
  }

  public void updateFeederInputs() {
    if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_FEEDER)) {
      feeder.feed();
    } else if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_FEEDER_BACKWARDS)) {
      feeder.unfeed();
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
    } else if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.RUN_NECK_BACKWARDS)) {
      neck.unneck();
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
    } else if (secondary_joy.getRawButton(CONTROLS.SECONDARY_JOY.BUTTON_IDS.MANUAL_TURRET)) {
      turret.getController().manual(secondary_joy.getX() * 0.1);
    } else {
      turret.getController().stable();
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