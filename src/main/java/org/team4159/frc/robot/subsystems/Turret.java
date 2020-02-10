package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {
  private TalonFX turret_falcon;

  private Limelight limelight;

  private TalonFX configureTalonFX(TalonFX talonFX) {
    talonFX.configFactoryDefault();
    talonFX.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    talonFX.setNeutralMode(NeutralMode.Brake);

    return talonFX;
  }

  public Turret() {
    limelight = new Limelight();
    turret_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.TURRET_FALCON_ID));
  }

  @Override
  public void periodic() {
    if (isForwardLimitSwitchClosed()) {
      zeroEncoder();
    } else {
      setEncoderPosition(TURRET_CONSTANTS.TICK_RANGE);
    }

    double distance = getDistanceToVisionTarget();
    SmartDashboard.putNumber("distance in inches", distance);
    SmartDashboard.putNumber("distance in feet", distance / 12);
  }

  public void setRawSpeed(double speed) {
    turret_falcon.set(ControlMode.PercentOutput, speed);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public void setEncoderPosition(double position) {
    turret_falcon.setSelectedSensorPosition(0);
  }

  public void zeroEncoder() {
    setEncoderPosition(0);
  }

  public boolean isForwardLimitSwitchClosed() {
    return turret_falcon.isFwdLimitSwitchClosed() == 1;
  }

  public boolean isReverseLimitSwitchClosed() {
    return turret_falcon.isRevLimitSwitchClosed() == 1;
  }

  // not really sure where to put these limelight methods

  public double getDistanceToVisionTarget() {
    double vertical_offset = limelight.getTargetVerticalOffset();
    double total_angle_to_target = LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset;
    return LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT / Math.tan(total_angle_to_target);
  }

  public Limelight getLimelight() {
    return limelight;
  }
}
