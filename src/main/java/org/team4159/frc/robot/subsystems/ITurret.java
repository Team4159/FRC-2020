package org.team4159.frc.robot.subsystems;

import org.team4159.lib.hardware.Limelight;

public interface ITurret {
  void setRawSpeed(double speed);
  void stop();
  void setEncoderPosition(int position);
  int getPosition();
  boolean isForwardLimitSwitchClosed();
  boolean isReverseLimitSwitchClosed();
  Limelight getLimelight();
}
