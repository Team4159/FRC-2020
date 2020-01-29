package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Arm extends SubsystemBase {
  private TalonSRX arm_talon;
  private DigitalInput limit_switch;

  private int setpoint = 0;

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Brake);

    return talonSRX;
  }

  public Arm() {
    arm_talon = configureTalonSRX(new TalonSRX(10));
    arm_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    int PID_IDX = 0;
    arm_talon.config_kP(PID_IDX, ARM_CONSTANTS.kP);
    arm_talon.config_kP(PID_IDX, ARM_CONSTANTS.kI);
    arm_talon.config_kP(PID_IDX, ARM_CONSTANTS.kD);

    limit_switch = new DigitalInput(ARM_CONSTANTS.LIMIT_SWITCH_PORT);
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("arm_position", arm_talon.getSelectedSensorPosition());
  }

  public void setRawSpeed(double speed) {
    arm_talon.set(ControlMode.PercentOutput, speed);
  }

  public void updateMotionMagic() {
    arm_talon.set(ControlMode.MotionMagic, setpoint);
  }

  public void raiseIntake() {
    setpoint = ARM_CONSTANTS.UP_POSITION;
    updateMotionMagic();
  }

  public void lowerIntake() {
    setpoint = ARM_CONSTANTS.DOWN_POSITION;
    updateMotionMagic();
  }

  public void zeroEncoder() {
    arm_talon.setSelectedSensorPosition(0);
  }

  public int getSetpoint() {
    return setpoint;
  }

  public boolean isLimitSwitchClosed() {
    return limit_switch.get();
  }
}
