package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase implements IIntake {
  private CANSparkMax intake_spark;

  public Intake() {
    intake_spark = new CardinalMAX(CAN_IDS.INTAKE_SPARK, CANSparkMax.IdleMode.kCoast);
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
