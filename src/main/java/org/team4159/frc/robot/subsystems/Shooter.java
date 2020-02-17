package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.EnhancedEncoder;
import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalSPX;
import org.team4159.lib.hardware.controller.ctre.CardinalSRX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private enum State {
    CLOSED_LOOP,
    IDLE
  }
  private State state = State.IDLE;

  private CardinalSRX primary_shooter_talon, shooter_talon_two;
  private CardinalSPX shooter_victor_one, shooter_victor_two;
  private SpeedControllerGroup shooter_motors;

  private EnhancedEncoder shooter_encoder;
  private Limelight limelight;

  private PIDController pid_controller;

  public Shooter(Limelight limelight) {
    this.limelight = limelight;

    primary_shooter_talon = new CardinalSRX(CAN_IDS.PRIMARY_SHOOTER_TALON, NeutralMode.Coast);
    shooter_talon_two = new CardinalSRX(CAN_IDS.SHOOTER_TALON_TWO, NeutralMode.Coast);
    shooter_victor_one = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_ONE, NeutralMode.Coast);
    shooter_victor_two = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_TWO, NeutralMode.Coast);

    shooter_motors = new SpeedControllerGroup(
      primary_shooter_talon,
      shooter_talon_two,
      shooter_victor_one,
      shooter_victor_two
    );

    shooter_encoder = new EnhancedEncoder(new Encoder(
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      SHOOTER_CONSTANTS.IS_ENCODER_REVERSED,
      SHOOTER_CONSTANTS.ENCODER_ENCODING_TYPE
    ));

    pid_controller = new PIDController(
      SHOOTER_CONSTANTS.kP,
      SHOOTER_CONSTANTS.kI,
      SHOOTER_CONSTANTS.kD
    );
    pid_controller.setTolerance(SHOOTER_CONSTANTS.ACCEPTABLE_SPEED_ERROR);

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber(
      "current_shooter_speed_rpm",
      shooter_encoder.getVelocity() * SHOOTER_CONSTANTS.COUNTS_PER_SECOND_TO_RPM
    );
    double distance = getDistanceToVisionTarget();
    SmartDashboard.putNumber("distance in inches", distance);
    SmartDashboard.putNumber("distance in feet", distance / 12);

    double output_voltage = 0;

    switch (state) {
      case CLOSED_LOOP:
        output_voltage = pid_controller.calculate(
          shooter_encoder.getVelocity() * SHOOTER_CONSTANTS.COUNTS_PER_SECOND_TO_RPM
        );
        break;
      case IDLE:
        break;
    }

    setRawVoltage(output_voltage);
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  public void setRawVoltage(double voltage) {
    shooter_motors.setVoltage(voltage);
  }

  public void setTargetSpeed(double speed) {
    pid_controller.setSetpoint(speed);
  }

  public double getDistanceToVisionTarget() {
    double vertical_offset = limelight.getTargetVerticalOffset();
    double total_angle_to_target = LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset;
    return LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT / Math.tan(total_angle_to_target);
  }

  public boolean isAtTargetSpeed() {
    return pid_controller.atSetpoint();
  }

  public int getSetpoint() {
    return (int) pid_controller.getSetpoint();
  }
}
