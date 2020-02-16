package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.controller.ctre.CardinalSRX;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Feeder extends SubsystemBase {
  private SpeedControllerGroup feeder_motors;

  public Feeder() {
    CardinalMAX spark = new CardinalMAX(CAN_IDS.UPPER_FEEDER_SPARK, CANSparkMax.IdleMode.kCoast);

    spark.setInverted(true);

    feeder_motors = new SpeedControllerGroup(
      new CardinalSRX(CAN_IDS.LOWER_FEEDER_TALON, NeutralMode.Brake)
      ,spark
    );
  }

  public void setRawSpeed(double speed) {
    feeder_motors.set(speed);
  }

  public void feed() {
    setRawSpeed(0.8);
  }

  public void stop() {
    setRawSpeed(0);
  }
}
