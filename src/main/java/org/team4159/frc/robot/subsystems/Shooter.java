package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.lib.control.PIDControl;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private CANEncoder primary_shooter_encoder, secondary_shooter_encoder;

  private SpeedControllerGroup shooter_motors;

  private PIDControl pid;

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
    pid = new PIDControl(
      SHOOTER_CONSTANTS.kP,
      SHOOTER_CONSTANTS.kI,
      SHOOTER_CONSTANTS.kD
    );

    shooter_motors = new SpeedControllerGroup(
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.SHOOTER_TALON_ONE_ID)),
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.SHOOTER_TALON_TWO_ID)),
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
    setRawSpeed(pid.calculateOutput(getVelocity()));

    SmartDashboard.putNumber("current_shooter_speed", getVelocity());
    pid.setGoal(SmartDashboard.getNumber("target_shooter_speed", 0));
    pid.setP(SmartDashboard.getNumber("shooter_kp", SHOOTER_CONSTANTS.kP));
    pid.setI(SmartDashboard.getNumber("shooter_ki", SHOOTER_CONSTANTS.kI));
    pid.setD(SmartDashboard.getNumber("shooter_kd", SHOOTER_CONSTANTS.kD));
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  private double getVelocity() {
    return 0;
  }
}
