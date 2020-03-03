package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.frc.robot.controllers.TurretController;
import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {
  private TalonFX turret_falcon;

  private TurretController turret_controller;

  public Turret() {
    turret_falcon = new CardinalFX(CAN_IDS.TURRET_FALCON, NeutralMode.Brake);
    turret_falcon.setInverted(true);

    turret_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    turret_controller = new TurretController(this, Limelight.getDefault());
  }

  @Override
  public void periodic() {
    turret_controller.update();
  }

  public void setRawSpeed(double speed) {
    turret_falcon.set(ControlMode.PercentOutput, speed);
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

  public TurretController getController() {
    return turret_controller;
  }
}
