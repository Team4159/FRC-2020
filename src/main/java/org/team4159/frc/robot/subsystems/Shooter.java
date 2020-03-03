package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMaxLowLevel;
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
  private CANSparkMax shooter_spark_one, shooter_spark_two;
  private SpeedControllerGroup shooter_motors;

  private EnhancedEncoder shooter_encoder;

  private ShooterController shooter_controller;

  public Shooter() {
    shooter_spark_one = new CANSparkMax(CAN_IDS.SHOOTER_SPARK_ONE, CANSparkMaxLowLevel.MotorType.kBrushless);
    shooter_spark_two = new CANSparkMax(CAN_IDS.SHOOTER_SPARK_TWO, CANSparkMaxLowLevel.MotorType.kBrushless);

    shooter_spark_one.setSmartCurrentLimit(40);
    shooter_spark_two.setSmartCurrentLimit(40);
    shooter_spark_one.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shooter_spark_two.setIdleMode(CANSparkMax.IdleMode.kCoast);
    shooter_spark_one.setInverted(true);
    shooter_spark_one.burnFlash();
    shooter_spark_two.burnFlash();

    // shooter_motors = new SpeedControllerGroup(shooter_spark_one, shooter_spark_two);

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
    shooter_spark_one.set(speed);
    shooter_spark_two.set(speed);
  }

  public void setRawVoltage(double voltage) {
    shooter_spark_one.set(voltage);
    shooter_spark_two.set(voltage);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public double getSpeed() {
    return -shooter_encoder.getVelocity() * SHOOTER_CONSTANTS.COUNTS_PER_SECOND_TO_RPM;
  }

  public ShooterController getController() {
    return shooter_controller;
  }
}
