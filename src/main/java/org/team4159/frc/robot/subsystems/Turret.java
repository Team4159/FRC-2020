package org.team4159.frc.robot.subsystems;

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

  private Limelight limelight;

  public Turret(Limelight limelight) {
    this.limelight = limelight;
    turret_falcon = new CardinalFX(CAN_IDS.TURRET_FALCON, NeutralMode.Brake);

    turret_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    turret_falcon.setSensorPhase(false);

    // CHECK AND FIX THIS!?!?!??!?!?!
    // POSITIVE INPUT = ROTATE CLOCKWISE
    // ENCODER POSITIVE = ROTATE COUNTERCLOCKWISE
  }

  @Override
  public void periodic() {
    if (isForwardLimitSwitchClosed()) {
      setEncoderPosition(TURRET_CONSTANTS.FORWARD_POSITION);
    } else if (isReverseLimitSwitchClosed()) {
      setEncoderPosition(TURRET_CONSTANTS.REVERSE_POSITION);
    }
  }

  public void setRawSpeed(double speed) {
    turret_falcon.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public void setEncoderPosition(double position) {
    turret_falcon.setSelectedSensorPosition(0);
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

  // not really sure where to put these limelight methods
  public Limelight getLimelight() {
    return limelight;
  }
}
