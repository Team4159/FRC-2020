package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Feeder extends SubsystemBase {
  public TalonSRX feeder_talon_1;
  public TalonSRX feeder_talon_2;

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Brake);

    return talonSRX;
  }

  public Feeder() {
    feeder_talon_1 = configureTalonSRX(new TalonSRX(CAN_IDS.FEEDER_TALON_ONE_ID));
    feeder_talon_2 = configureTalonSRX(new TalonSRX(CAN_IDS.FEEDER_TALON_TWO_ID));
  }

  public void feed() {
    setFeederSpeed(1);
  }

  public void stop() {
    setFeederSpeed(0);
  }

  public void setFeederSpeed(double speed) {
    feeder_talon_1.set(ControlMode.PercentOutput, speed);
    feeder_talon_2.set(ControlMode.PercentOutput, speed);
  }
}
