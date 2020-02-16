package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
  private CANSparkMax intake_spark;

  public Intake() {
    intake_spark = new CardinalMAX(CAN_IDS.INTAKE_SPARK, CANSparkMax.IdleMode.kCoast);
  }

  public void setRawSpeed(double speed) {
    intake_spark.set(speed);
  }

  public void intake() {
    setRawSpeed(1);
  }

  public void stop() {
    setRawSpeed(0);
  }
}
