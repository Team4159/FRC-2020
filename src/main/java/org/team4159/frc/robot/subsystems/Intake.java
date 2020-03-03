package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
  private CANSparkMax intake_spark;

  public Intake() {
    intake_spark = new CANSparkMax(CAN_IDS.INTAKE_SPARK, CANSparkMaxLowLevel.MotorType.kBrushless);
    intake_spark.setInverted(false);
    intake_spark.setSmartCurrentLimit(40);
    intake_spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
    intake_spark.burnFlash();
  }

  public void setRawSpeed(double speed) {
    intake_spark.set(speed);
  }

  public void intake() {
    setRawSpeed(INTAKE_CONSTANTS.INTAKE_SPEED);
  }

  public void stop() {
    setRawSpeed(0);
  }
}
