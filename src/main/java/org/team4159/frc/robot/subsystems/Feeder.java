package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.team4159.lib.hardware.controller.ctre.CardinalSRX;

import static org.team4159.frc.robot.Constants.*;

public class Feeder extends SubsystemBase {
  private SpeedControllerGroup feeder_motors;

  public Feeder() {
    TalonSRX feeder_talon_1, feeder_talon_2;

    feeder_talon_1 = new CardinalSRX(CAN_IDS.FEEDER_TALON_ONE, NeutralMode.Brake);
    feeder_talon_2 = new CardinalSRX(CAN_IDS.FEEDER_TALON_TWO, NeutralMode.Brake);

    feeder_motors = new SpeedControllerGroup(
      (WPI_TalonSRX) feeder_talon_1,
      (WPI_TalonSRX) feeder_talon_2
    );
  }

  public void feed() {
    setFeederSpeed(1);
  }

  public void stop() {
    setFeederSpeed(0);
  }

  public void setFeederSpeed(double speed) {
    feeder_motors.set(speed);
  }
}
