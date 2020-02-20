package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import org.team4159.lib.control.signal.DriveSignal;

public interface IDrivetrain {
  void drive(DriveSignal drive_signal);
  void resetEncoders();
  void resetDirection();
  void zeroSensors();
  DifferentialDriveWheelSpeeds getWheelSpeeds();
  double getDirection();
  double getLeftVoltage();
  double getRightVoltage();
  double getLeftDistance();
  double getLeftVelocity();
  double getRightDistance();
  double getRightVelocity();
  Pose2d getPose();
}
