package org.team4159.frc.robot.subsystems;

public interface IArm {
  void setRawSpeed(double speed);
  void setRawVoltage(double voltage);
  void zeroEncoder();
  int getPosition();
  boolean isLimitSwitchClosed();
}
