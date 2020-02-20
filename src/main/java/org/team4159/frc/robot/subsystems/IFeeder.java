package org.team4159.frc.robot.subsystems;

public interface IFeeder {
  void setRawSpeed(double speed);
  void feed();
  void stop();
}
