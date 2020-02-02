package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends PIDSubsystem {
  private CANSparkMax arm_spark;

  private DigitalInput arm_limit_switch;
  private Encoder arm_encoder;

  private CANSparkMax configureSparkMax(CANSparkMax spark) {
    spark.restoreFactoryDefaults();
    // spark.setSmartCurrentLimit(40);
    spark.setIdleMode(CANSparkMax.IdleMode.kBrake);
    spark.burnFlash();

    return spark;
  }

  public Arm() {
    super(new PIDController(ARM_CONSTANTS.kP, 0, ARM_CONSTANTS.kD));

    arm_spark = configureSparkMax(new CANSparkMax(
      CAN_IDS.ARM_SPARK_ID,
      CANSparkMax.MotorType.kBrushless
    ));

    arm_encoder = new Encoder(
      ARM_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      ARM_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      ARM_CONSTANTS.IS_ENCODER_REVERSED,
      ARM_CONSTANTS.ENCODER_ENCODING_TYPE
    );

    arm_limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("arm_position", arm_encoder.get());

    if (isLimitSwitchClosed()) {
      zeroEncoder();
    }
  }

  public void setRawSpeed(double speed) {
    arm_spark.set(speed);
  }

  public void setRawVoltage(double voltage) {
    arm_spark.setVoltage(voltage);
  }

  public void raiseIntake() {
    setSetpoint(ARM_CONSTANTS.UP_POSITION);
  }

  public void lowerIntake() {
    setSetpoint(ARM_CONSTANTS.DOWN_POSITION);
  }

  public void zeroEncoder() {
    arm_encoder.reset();
  }

  public int getPosition() {
    return arm_encoder.get();
  }

  public boolean isLimitSwitchClosed() {
    return !arm_limit_switch.get();
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    setRawVoltage(output);
  }

  @Override
  protected double getMeasurement() {
    return getPosition();
  }
}
