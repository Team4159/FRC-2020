package org.team4159.frc.robot.subsystems;

import com.revrobotics.EncoderType;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {
  private CANEncoder primary_shooter_encoder, secondary_shooter_encoder;

  private SpeedControllerGroup shooter_motors;

  private CANSparkMax configureSparkMax(CANSparkMax spark) {
    spark.restoreFactoryDefaults();
    spark.setSmartCurrentLimit(40);
    spark.setIdleMode(CANSparkMax.IdleMode.kCoast);
    spark.burnFlash();

    return spark;
  }

  public Shooter() {
    super(new PIDController(
      SHOOTER_CONSTANTS.kP,
      SHOOTER_CONSTANTS.kI,
      SHOOTER_CONSTANTS.kD
    ));

    CANSparkMax primary_shooter_spark, secondary_shooter_spark;

    primary_shooter_spark = configureSparkMax(
      new CANSparkMax(CAN_IDS.LEFT_SHOOTER_SPARK_ID, MotorType.kBrushless));
    secondary_shooter_spark = configureSparkMax(
      new CANSparkMax(CAN_IDS.RIGHT_SHOOTER_SPARK_ID, MotorType.kBrushless));

    secondary_shooter_spark.setInverted(true);

    primary_shooter_encoder = primary_shooter_spark.getEncoder(EncoderType.kHallSensor, 42);
    secondary_shooter_encoder = secondary_shooter_spark.getEncoder(EncoderType.kHallSensor, 42);

    shooter_motors = new SpeedControllerGroup(
      primary_shooter_spark,
      secondary_shooter_spark
    );

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("current_shooter_speed", getVelocity());
    setSetpoint(SmartDashboard.getNumber("target_shooter_speed", 0));
    getController().setP(SmartDashboard.getNumber("shooter_kp", SHOOTER_CONSTANTS.kP));
    getController().setI(SmartDashboard.getNumber("shooter_ki", SHOOTER_CONSTANTS.kI));
    getController().setD(SmartDashboard.getNumber("shooter_kd", SHOOTER_CONSTANTS.kD));
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  private double getVelocity() {
    return (primary_shooter_encoder.getVelocity() + secondary_shooter_encoder.getVelocity()) / 2.0;
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    setRawSpeed(output);
  }

  @Override
  protected double getMeasurement() {
    return getVelocity();
  }
}
