package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import org.team4159.frc.robot.controllers.ShooterController;
import org.team4159.lib.hardware.EnhancedEncoder;
import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private CardinalMAX shooter_spark_one, shooter_spark_two;
  private SpeedControllerGroup shooter_motors;

  private EnhancedEncoder shooter_encoder;

  private ShooterController shooter_controller;

  public Shooter() {
    shooter_spark_one = new CardinalMAX(CAN_IDS.SHOOTER_SPARK_ONE, CANSparkMax.IdleMode.kCoast);
    shooter_spark_two = new CardinalMAX(CAN_IDS.SHOOTER_SPARK_TWO, CANSparkMax.IdleMode.kCoast);
    shooter_spark_two.setInverted(true);

    shooter_motors = new SpeedControllerGroup(shooter_spark_one, shooter_spark_two);

    shooter_encoder = new EnhancedEncoder(new Encoder(
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      SHOOTER_CONSTANTS.IS_ENCODER_REVERSED,
      SHOOTER_CONSTANTS.ENCODER_ENCODING_TYPE
    ));

    shooter_controller = new ShooterController(this, Limelight.getDefault());
  }

  @Override
  public void periodic() {
    shooter_controller.update();
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  public void setRawVoltage(double voltage) {
    shooter_motors.setVoltage(voltage);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public double getSpeed() {
    return shooter_encoder.getVelocity();
  }

  public ShooterController getController() {
    return shooter_controller;
  }
}
