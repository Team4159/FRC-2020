package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.team4159.lib.hardware.util.ControllerUtil;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private TalonSRX left_shooter_talon, right_shooter_talon;
  private VictorSPX left_shooter_victor, right_shooter_victor;

  private double goal_speed = 0.0;
  private boolean enabled = false;

  public Shooter() {
    left_shooter_talon = ControllerUtil.configureTalonSRX(new TalonSRX(CAN_IDS.SHOOTER_TALON_ONE_ID));
    right_shooter_talon = ControllerUtil.configureTalonSRX(new TalonSRX(CAN_IDS.SHOOTER_TALON_TWO_ID));

    left_shooter_victor = ControllerUtil.configureVictorSPX(new VictorSPX(CAN_IDS.SHOOTER_VICTOR_ONE_ID));
    right_shooter_victor = ControllerUtil.configureVictorSPX(new VictorSPX(CAN_IDS.SHOOTER_VICTOR_TWO_ID));

    right_shooter_talon.follow(left_shooter_talon);
    left_shooter_victor.follow(left_shooter_talon);
    right_shooter_victor.follow(right_shooter_victor);

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  @Override
  public void periodic() {
    if (enabled) {
      setTargetSpeed(goal_speed);
    } else {
      setRawSpeed(0);
    }

    SmartDashboard.putNumber("current_shooter_speed", getPosition());
    setGoal(SmartDashboard.getNumber("target_shooter_speed", 0));
    setP(SmartDashboard.getNumber("shooter_kp", SHOOTER_CONSTANTS.kP));
    setI(SmartDashboard.getNumber("shooter_ki", SHOOTER_CONSTANTS.kI));
    setD(SmartDashboard.getNumber("shooter_kd", SHOOTER_CONSTANTS.kD));
  }

  public void setRawSpeed(double percent) {
    left_shooter_talon.set(ControlMode.PercentOutput, percent);
  }

  public void setTargetSpeed(double speed) {
    left_shooter_talon.set(ControlMode.MotionMagic, goal_speed);
  }

  public void setP(double kP) {
    left_shooter_talon.config_kP(0, kP);
  }

  public void setI(double kI) {
    left_shooter_talon.config_kI(0, kI);
  }

  public void setD(double kD) {
    left_shooter_talon.config_kD(0, kD);
  }

  public void setGoal(double goal) {
    goal_speed = goal;
  }

  public double getPosition() {
    return (left_shooter_talon.getSelectedSensorVelocity() + right_shooter_talon.getSelectedSensorVelocity()) / 2.0;
  }

  public void enable() {
    enabled = true;
  }

  public void disable() {
    enabled = false;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
