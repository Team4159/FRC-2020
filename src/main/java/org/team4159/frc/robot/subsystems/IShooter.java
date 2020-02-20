package org.team4159.frc.robot.subsystems;

import org.team4159.lib.hardware.Limelight;

public interface IShooter {
  void setRawSpeed(double speed);
  void setRawVoltage(double voltage);
  double getSpeed();
  Limelight getLimelight();
}
