package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import org.team4159.frc.robot.Robot;
import org.team4159.lib.hardware.controller.ctre.CardinalSPX;
import org.team4159.lib.hardware.controller.ctre.CardinalSRX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {
  private TalonSRX primary_shooter_talon;
  private Encoder shooter_encoder;

  private int last_position;

  public Shooter() {
    super(new PIDController(
        SHOOTER_CONSTANTS.kP,
        SHOOTER_CONSTANTS.kI,
        SHOOTER_CONSTANTS.kD
      )
    );

    TalonSRX shooter_talon_two;
    VictorSPX shooter_victor_one, shooter_victor_two;

    primary_shooter_talon = new CardinalSRX(CAN_IDS.PRIMARY_SHOOTER_TALON, NeutralMode.Coast);
    shooter_talon_two = new CardinalSRX(CAN_IDS.SHOOTER_TALON_TWO, NeutralMode.Coast);

    shooter_victor_one = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_ONE, NeutralMode.Coast);
    shooter_victor_two = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_TWO, NeutralMode.Coast);

    shooter_talon_two.follow(primary_shooter_talon);
    shooter_victor_one.follow(primary_shooter_talon);
    shooter_victor_two.follow(primary_shooter_talon);

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
    SmartDashboard.putNumber("current_shooter_speed", getVelocity());
  }

  public void setRawSpeed(double percent) {
    primary_shooter_talon.set(ControlMode.PercentOutput, percent);
  }

  public void setTargetSpeed(double speed) {
    super.setSetpoint(speed);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public int getPosition() {
    return shooter_encoder.get();
  }

  public double getVelocity() {
    double delta = (getPosition() - last_position) / Robot.kDefaultPeriod;

    last_position = getPosition();

    return delta;
  }

  @Override
  protected double getMeasurement() {
    return getVelocity();
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    setRawSpeed(output);
  }

  @Override
  public void enable() {
    last_position = getPosition();

    super.enable();
  }
}
