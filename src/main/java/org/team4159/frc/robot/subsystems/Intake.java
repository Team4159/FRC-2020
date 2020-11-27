package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.frc.robot.Robot;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
  private CardinalMAX intake_spark;

  public Intake() {
    if (Robot.isSimulation()) {
      return;
    }

    intake_spark = new CardinalMAX(CAN_IDS.INTAKE_SPARK, CANSparkMax.IdleMode.kCoast, false);
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
