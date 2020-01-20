package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {
  private TalonFX turret_falcon;

  public Turret() {
    turret_falcon = configureTalonFX(new TalonFX(CAN_IDS.TURRET_FALCON_ID));
  }

  private TalonFX configureTalonFX(TalonFX talonFX) {
    talonFX.configFactoryDefault();
    talonFX.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    talonFX.setNeutralMode(NeutralMode.Brake);

    return talonFX;
  }

  public void setRawSpeed(double speed) {
    turret_falcon.set(TalonFXControlMode.PercentOutput, speed);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public boolean isForwardLimitSwitchClosed() {
    return turret_falcon.isFwdLimitSwitchClosed() == 1;
  }

  public boolean isReverseLimitSwitchClosed() {
    return turret_falcon.isRevLimitSwitchClosed() == 1;
  }

  public void zeroSensors() {
    turret_falcon.setSelectedSensorPosition(0);
  }
}
