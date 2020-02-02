package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
  private CANSparkMax intake_spark;

  private CANSparkMax configureSparkMax(CANSparkMax spark) {
    spark.restoreFactoryDefaults();
    spark.setSmartCurrentLimit(40);
    spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
    spark.burnFlash();

    return spark;
  }

  public Intake() {
    intake_spark = configureSparkMax(new CANSparkMax(CAN_IDS.INTAKE_SPARK_ID, CANSparkMax.MotorType.kBrushless));
  }

  public void setRawIntakeSpeed(double speed) {
    intake_spark.set(speed);
  }

  public void intakeCell() {
    setRawIntakeSpeed(1);
  }

  public void stopIntaking() {
    setRawIntakeSpeed(0);
  }
}
