package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Neck extends SubsystemBase {
  private CardinalMAX neck_spark;

  public Neck() {
    neck_spark = new CardinalMAX(CAN_IDS.NECK_SPARK, CANSparkMax.IdleMode.kCoast, true);
  }

  public void setRawSpeed(double speed) {
    neck_spark.set(speed);
  }

  public void neck() {
    setRawSpeed(1);
  }

  public void unneck() {
    setRawSpeed(-1);
  }

  public void stop() {
    setRawSpeed(0);
  }
}
