package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {
  private TalonFX turret_falcon;

  private Limelight limelight;
  private boolean seeking = false;

  public Turret() {
    limelight = new Limelight();
    turret_falcon = new CardinalFX(CAN_IDS.TURRET_FALCON, NeutralMode.Brake);
  }

  @Override
  public void periodic() {
    if (isForwardLimitSwitchClosed()) {
      setEncoderPosition(TURRET_CONSTANTS.TICK_RANGE);
    } else if (isReverseLimitSwitchClosed()) {
      zeroEncoder();
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

  public void toggleSeeking() {
    seeking = !seeking;
  }

  public boolean isSeeking() {
    return seeking;
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
