package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4159.lib.control.PIDControl;

import org.team4159.lib.control.subsystem.PIDSubsystem;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends PIDSubsystem {
  private CANSparkMax arm_spark;
  private DigitalInput arm_limit_switch;
  private Encoder arm_encoder;

  private boolean is_zeroed = false;

  private CANSparkMax configureSparkMax(CANSparkMax spark) {
    spark.restoreFactoryDefaults();
    //spark.setSmartCurrentLimit(40);
    spark.setIdleMode(CANSparkMax.IdleMode.kBrake);
    spark.burnFlash();

    return spark;
  }

  public Arm() {
    super(
      ARM_CONSTANTS.kP,
      ARM_CONSTANTS.kI,
      ARM_CONSTANTS.kD
    );

    arm_encoder = new Encoder(
      ARM_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      ARM_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      ARM_CONSTANTS.IS_ENCODER_REVERSED,
      ARM_CONSTANTS.ENCODER_ENCODING_TYPE
    );

    arm_spark = configureSparkMax(new CANSparkMax(CAN_IDS.ARM_SPARK_ID, CANSparkMax.MotorType.kBrushless));

    arm_limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);

    raiseIntake();
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("arm_position", arm_encoder.get());
  }

  public void setRawSpeed(double speed) {
    arm_spark.set(speed);
  }

  public void setRawVoltage(double voltage) {
    arm_spark.setVoltage(voltage);
  }

  public void raiseIntake() {
    pid_control.setGoal(ARM_CONSTANTS.UP_POSITION);
  }

  public void lowerIntake() {
    pid_control.setGoal(ARM_CONSTANTS.DOWN_POSITION);
  }

  public int getSetpoint() {
    return (int) pid_control.getGoal();
  }

  public boolean isLimitSwitchClosed() {
    return !arm_limit_switch.get();
  }

  @Override
  public void zeroSubsystem() {
    super.zeroSubsystem();

    arm_encoder.reset();
  }

  @Override
  public double getPosition() {
    return arm_encoder.get();
  }

  @Override
  public void setOutput(double voltage) {
    setRawVoltage(voltage);
  }
}
