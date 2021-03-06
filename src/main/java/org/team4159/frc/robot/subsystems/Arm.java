package org.team4159.frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.team4159.frc.robot.controllers.ArmController;
import org.team4159.lib.hardware.controller.rev.CardinalMAX;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends SubsystemBase {
  private CardinalMAX arm_spark;

  private DigitalInput arm_limit_switch;
  private Encoder arm_encoder;

  private ArmController arm_controller;

  public Arm() {
    init();
  }

  protected void init() {
    arm_spark = new CardinalMAX(CAN_IDS.ARM_SPARK, CANSparkMax.IdleMode.kCoast, true);

    arm_limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);
    arm_encoder = new Encoder(
      ARM_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      ARM_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      ARM_CONSTANTS.IS_ENCODER_REVERSED,
      ARM_CONSTANTS.ENCODER_ENCODING_TYPE
    );
    arm_spark.setInverted(false);

    arm_controller = new ArmController(this);
  }

  @Override
  public void periodic() {
    arm_controller.update();
    SmartDashboard.putNumber("arm_encoder", arm_encoder.get());
    SmartDashboard.putBoolean("arm_lim_switch", isLimitSwitchClosed());
  }

  // motor setters

  public void setRawSpeed(double speed) {
    arm_spark.set(speed);
  }

  public void setRawVoltage(double voltage) {
    arm_spark.setVoltage(voltage);
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

  public ArmController getController() {
    return arm_controller;
  }
}
