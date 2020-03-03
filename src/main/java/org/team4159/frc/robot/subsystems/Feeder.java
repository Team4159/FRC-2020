package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMax;

import org.team4159.lib.hardware.controller.ctre.CardinalSRX;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Feeder extends SubsystemBase {
  private CardinalMAX tower_spark;
  private CardinalSRX floor_talon;

  public Feeder() {
    tower_spark = new CardinalMAX(CAN_IDS.UPPER_FEEDER_SPARK, CANSparkMax.IdleMode.kCoast);
    floor_talon = new CardinalSRX(CAN_IDS.LOWER_FEEDER_TALON, NeutralMode.Brake);
  }

  public void setRawTowerSpeed(double speed) {
    tower_spark.set(speed);
  }

  public void setRawFloorSpeed(double speed) {
    floor_talon.set(speed);
  }

  public void feed() {
    setRawTowerSpeed(-FEEDER_CONSTANTS.TOWER_FEEDING_SPEED);
    setRawFloorSpeed(FEEDER_CONSTANTS.FLOOR_FEEDING_SPEED);
  }

  public void stop() {
    setRawTowerSpeed(0);
    setRawFloorSpeed(0);
  }
}
