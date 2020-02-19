package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {

  private TalonFX turret_falcon;

  private boolean recovering = false;
  private boolean zeroed = false;

  private Limelight limelight;

  public Turret(Limelight limelight) {
    this.limelight = limelight;
    turret_falcon = new CardinalFX(CAN_IDS.TURRET_FALCON, NeutralMode.Brake);

    turret_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    turret_falcon.setInverted(false);
    // POSITIVE = COUNTER CLOCKWISE
  }

  @Override
  public void periodic() {
    if (isForwardLimitSwitchClosed()) {
      setEncoderPosition(TURRET_CONSTANTS.FORWARD_POSITION);
    } else if (isReverseLimitSwitchClosed()) {
      setEncoderPosition(TURRET_CONSTANTS.REVERSE_POSITION);
    }


    if (getPosition() > TURRET_CONSTANTS.SAFE_FORWARD_POSITION || getPosition() < TURRET_CONSTANTS.SAFE_REVERSE_POSITION) {
      if (zeroed) recovering = true;
    } else {
      recovering = false;

      setRawSpeed(0);
    }

    if (recovering) {
      if (getPosition() < TURRET_CONSTANTS.SAFE_FORWARD_POSITION) {
        turret_falcon.set(ControlMode.PercentOutput, TURRET_CONSTANTS.ZEROING_SPEED);
      } else {
        turret_falcon.set(ControlMode.PercentOutput, -1.0 * TURRET_CONSTANTS.ZEROING_SPEED);
      }
    }

    System.out.println(getPosition() + ", am i recovering: " + recovering);
  }

  // motor setters

  public void setRawSpeed(double speed) {
    if (!recovering) {
      turret_falcon.set(ControlMode.PercentOutput, -1.0 * speed);
    }
  }

  public void stop() {
    setRawSpeed(0);
  }

  // control methods

  public void setEncoderPosition(int position) {
    turret_falcon.setSelectedSensorPosition(position);
  }

  public int getPosition() {
    return turret_falcon.getSelectedSensorPosition();
  }

  public boolean isForwardLimitSwitchClosed() {
    return turret_falcon.isFwdLimitSwitchClosed() == 1;
  }

  public boolean isReverseLimitSwitchClosed() {
    return turret_falcon.isRevLimitSwitchClosed() == 1;
  }

  public boolean isPointingAtTarget() {
    return Math.abs(limelight.getTargetHorizontalOffset()) < 1;
  }

  public boolean isOutOfSafeRange() {
    return Math.abs(getPosition()) < TURRET_CONSTANTS.SAFE_FORWARD_POSITION;
  }

  // not really sure where to put these limelight methods
  public Limelight getLimelight() {
    return limelight;
  }

  public void setZeroed(boolean zeroed) {
    this.zeroed = zeroed;
  }
}
