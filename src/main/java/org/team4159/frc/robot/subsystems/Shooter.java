package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private TalonSRX shooter_talon_one, shooter_talon_two;
  private VictorSPX shooter_victor_one, shooter_victor_two;

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  private VictorSPX configureVictorSPX(VictorSPX victorSPX) {
    victorSPX.configFactoryDefault();
    victorSPX.setNeutralMode(NeutralMode.Coast);

    return victorSPX;
  }

  public Shooter() {
    shooter_talon_one = configureTalonSRX(new TalonSRX(CAN_IDS.SHOOTER_TALON_ONE_ID));
    shooter_talon_two = configureTalonSRX(new TalonSRX(CAN_IDS.SHOOTER_TALON_TWO_ID));

    shooter_victor_one = configureVictorSPX(new VictorSPX(CAN_IDS.SHOOTER_VICTOR_ONE_ID));
    shooter_victor_two = configureVictorSPX(new VictorSPX(CAN_IDS.SHOOTER_VICTOR_TWO_ID));

    shooter_talon_two.follow(shooter_talon_one);
    shooter_victor_one.follow(shooter_talon_one);
    shooter_victor_two.follow(shooter_victor_two);

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  public void setP(double kP) {
    shooter_talon_one.config_kP(0, kP);
  }

  public void setI(double kI) {
    shooter_talon_one.config_kI(0, kI);
  }

  public void setD(double kD) {
    shooter_talon_one.config_kD(0, kD);
  }

  public void setGoal(double goal) {
    shooter_talon_one.set(ControlMode.MotionMagic, goal);
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("current_shooter_speed", getPosition());
    setGoal(SmartDashboard.getNumber("target_shooter_speed", 0));
    setP(SmartDashboard.getNumber("shooter_kp", SHOOTER_CONSTANTS.kP));
    setI(SmartDashboard.getNumber("shooter_ki", SHOOTER_CONSTANTS.kI));
    setD(SmartDashboard.getNumber("shooter_kd", SHOOTER_CONSTANTS.kD));
  }

  public void setRawSpeed(double speed) {
    shooter_talon_one.set(ControlMode.PercentOutput, speed);
  }

  public double getPosition() {
    return (shooter_talon_one.getSelectedSensorVelocity() + shooter_talon_two.getSelectedSensorVelocity()) / 2.0;
  }
}
