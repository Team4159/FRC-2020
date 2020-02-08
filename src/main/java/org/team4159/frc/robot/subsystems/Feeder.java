package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team4159.lib.hardware.util.ControllerUtil;

import static org.team4159.frc.robot.Constants.*;

public class Feeder extends SubsystemBase {
  public TalonSRX feeder_talon;

  public Feeder() {
    feeder_talon = ControllerUtil.configureTalonSRX(new TalonSRX(CAN_IDS.FEEDER_TALON_ONE_ID), NeutralMode.Brake);
  }

  public void feedCell() {
    setFeederSpeed(1);
  }

  public void stop() {
    setFeederSpeed(0);
  }

  public void setFeederSpeed(double speed) {
    feeder_talon.set(ControlMode.PercentOutput, speed);
  }
}
