package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import org.team4159.frc.robot.Robot;
import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalSPX;
import org.team4159.lib.hardware.controller.ctre.CardinalSRX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {
  private TalonSRX primary_shooter_talon,shooter_talon_two;
  private VictorSPX shooter_victor_one, shooter_victor_two;
  private SpeedControllerGroup shooter_motors;
  private Encoder shooter_encoder;
  private Limelight limelight;

  private int last_position;

  public Shooter(Limelight limelight) {
    super(new PIDController(
        SHOOTER_CONSTANTS.kP,
        SHOOTER_CONSTANTS.kI,
        SHOOTER_CONSTANTS.kD
      )
    );

    this.limelight = limelight;

    primary_shooter_talon = new CardinalSRX(CAN_IDS.PRIMARY_SHOOTER_TALON, NeutralMode.Coast);
    shooter_talon_two = new CardinalSRX(CAN_IDS.SHOOTER_TALON_TWO, NeutralMode.Coast);

    shooter_victor_one = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_ONE, NeutralMode.Coast);
    shooter_victor_two = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_TWO, NeutralMode.Coast);

    shooter_motors = new SpeedControllerGroup(
      (WPI_TalonSRX) primary_shooter_talon,
      (WPI_TalonSRX) shooter_talon_two,
      (WPI_VictorSPX) shooter_victor_one,
      (WPI_VictorSPX) shooter_victor_two
    );

    shooter_encoder = new Encoder(
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      SHOOTER_CONSTANTS.IS_ENCODER_REVERSED,
      SHOOTER_CONSTANTS.ENCODER_ENCODING_TYPE
    );

    last_position = 0;

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("current_shooter_speed", getSpeed());
    double distance = getDistanceToVisionTarget();
    SmartDashboard.putNumber("distance in inches", distance);
    SmartDashboard.putNumber("distance in feet", distance / 12);

  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  public void setTargetSpeed(double speed) {
    setSetpoint(speed);
  }

  public void stop() {
    setSetpoint(0);
    setRawSpeed(0);
  }

  public int getPosition() {
    return shooter_encoder.get();
  }

  public double getSpeed() {
    double delta = (getPosition() - last_position) / Robot.kDefaultPeriod;
    last_position = getPosition();

    return delta;
  }

  public boolean isAtTargetSpeed() {
    return getController().atSetpoint();
  }

  public double getDistanceToVisionTarget() {
    double vertical_offset = limelight.getTargetVerticalOffset();
    double total_angle_to_target = LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset;
    return LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT / Math.tan(total_angle_to_target);
  }

  public Limelight getLimelight() {
    return limelight;
  }

  @Override
  protected double getMeasurement() {
    return getSpeed();
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    setRawSpeed(output);
  }

  @Override
  public void enable() {
    super.enable();
    last_position = getPosition();
  }
}
