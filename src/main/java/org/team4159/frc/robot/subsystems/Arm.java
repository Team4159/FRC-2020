package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends PIDSubsystem {
  private CANSparkMax arm_spark;
  private DigitalInput arm_limit_switch;
  private Encoder arm_encoder;

  public Arm() {
    super(new PIDController(
      ARM_CONSTANTS.kP,
      ARM_CONSTANTS.kI,
      ARM_CONSTANTS.kD
    ));

    arm_encoder = new Encoder(
      ARM_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      ARM_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      ARM_CONSTANTS.IS_ENCODER_REVERSED,
      ARM_CONSTANTS.ENCODER_ENCODING_TYPE
    );

    arm_spark = new CardinalMAX(CAN_IDS.ARM_SPARK_ID, CANSparkMax.IdleMode.kBrake);

    arm_spark.setInverted(true);

    arm_limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);

    raiseIntake();
  }

  @Override
  public void periodic() {
    super.periodic();

    if (isLimitSwitchClosed()) {
      zeroEncoder();
    }

    SmartDashboard.putBoolean("arm limit switch", isLimitSwitchClosed());
    SmartDashboard.putNumber("arm position", arm_encoder.get());
    SmartDashboard.putNumber("arm setpoint", getController().getSetpoint());
  }

  @Override
  public double getMeasurement() {
    return arm_encoder.get();
  }

  @Override
  public void useOutput(double voltage, double setpoint) {
    setRawVoltage(voltage);
  }

  public void setRawSpeed(double speed) {
    arm_spark.set(speed);
  }

  public void setRawVoltage(double voltage) {
    SmartDashboard.putNumber("arm voltage", voltage);

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

  public boolean isLimitSwitchClosed() {
    return !arm_limit_switch.get();
  }

  public double getSetpoint() {
    return m_controller.getSetpoint();
  }
}
