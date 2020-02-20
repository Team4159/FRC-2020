package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Neck extends SubsystemBase implements INeck {
  private CANSparkMax intake_spark;

  public Neck() {
    intake_spark = new CardinalMAX(CAN_IDS.NECK_SPARK, CANSparkMax.IdleMode.kCoast);
  }

  public void setRawSpeed(double speed) {
    intake_spark.set(speed);
  }

  public void neck() {
    setRawSpeed(1);
  }

  public void stop() {
    setRawSpeed(0);
  }
}
