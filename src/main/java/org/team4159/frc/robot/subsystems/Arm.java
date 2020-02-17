package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends SubsystemBase {
  private enum State {
    ZEROING,
    CLOSED_LOOP,
    ESTOP
  }
  private State state = State.ZEROING;

  private CANSparkMax arm_spark;
  private DigitalInput arm_limit_switch;
  private Encoder arm_encoder;

  private PIDController pid_controller;

  public Arm() {
    arm_spark = new CardinalMAX(CAN_IDS.ARM_SPARK, CANSparkMax.IdleMode.kBrake);
    arm_spark.setInverted(true);

    arm_limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);
    arm_encoder = new Encoder(
      ARM_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      ARM_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      ARM_CONSTANTS.IS_ENCODER_REVERSED,
      ARM_CONSTANTS.ENCODER_ENCODING_TYPE
    );

    pid_controller = new PIDController(
      ARM_CONSTANTS.kP,
      ARM_CONSTANTS.kI,
      ARM_CONSTANTS.kD
    );
  }

  @Override
  public void periodic() {
    if (isLimitSwitchClosed()) {
      zeroEncoder();
    }

    switch (state) {
      case ZEROING:
        setRawSpeed(ARM_CONSTANTS.ZEROING_SPEED);
        if (isLimitSwitchClosed()) {
          state = State.CLOSED_LOOP;
        }
        break;
      case CLOSED_LOOP:
        double output = pid_controller.calculate(arm_encoder.get());
        setRawVoltage(output);
        break;
    }
  }

  // motor setters

  public void setRawSpeed(double speed) {
    arm_spark.set(speed);
  }

  public void setRawVoltage(double voltage) {
    SmartDashboard.putNumber("arm voltage", voltage);

    arm_spark.setVoltage(voltage);
  }

  public void zeroEncoder() {
    arm_encoder.reset();
  }

  public boolean isLimitSwitchClosed() {
    return !arm_limit_switch.get();
  }

  public int getSetpoint() {
    return (int) pid_controller.getSetpoint();
  }

  public void setSetpoint(int setpoint) {
    pid_controller.setSetpoint(setpoint);
  }
}
