package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4159.lib.control.subsystem.PIDSubsystem;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {
  private SpeedControllerGroup shooter_motors;
  private TalonSRX shooter_talon_one, shooter_talon_two;

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
    super(
      SHOOTER_CONSTANTS.kP,
      SHOOTER_CONSTANTS.kI,
      SHOOTER_CONSTANTS.kD
    );

    super.zeroSubsystem();

    shooter_talon_one = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.SHOOTER_TALON_ONE_ID));
    shooter_talon_two = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.SHOOTER_TALON_TWO_ID));

    shooter_motors = new SpeedControllerGroup(
      (WPI_TalonSRX) shooter_talon_one,
      (WPI_TalonSRX) shooter_talon_two,
      (WPI_VictorSPX) configureVictorSPX(new WPI_VictorSPX(CAN_IDS.SHOOTER_VICTOR_ONE_ID)),
      (WPI_VictorSPX) configureVictorSPX(new WPI_VictorSPX(CAN_IDS.SHOOTER_VICTOR_TWO_ID))
    );

    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);
  }

  @Override
  public void periodic() {
    super.periodic();

    SmartDashboard.putNumber("current_shooter_speed", getVelocity());
    setGoal(SmartDashboard.getNumber("target_shooter_speed", 0));
    setP(SmartDashboard.getNumber("shooter_kp", SHOOTER_CONSTANTS.kP));
    setI(SmartDashboard.getNumber("shooter_ki", SHOOTER_CONSTANTS.kI));
    setD(SmartDashboard.getNumber("shooter_kd", SHOOTER_CONSTANTS.kD));
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  private double getVelocity() {
    return (shooter_talon_one.getSelectedSensorPosition() + shooter_talon_two.getSelectedSensorPosition()) / 2.0;
  }

  @Override
  public void setOutput(double output) {
    setRawSpeed(output);
  }

  @Override
  public double getPosition() {
    return getVelocity();
  }
}
