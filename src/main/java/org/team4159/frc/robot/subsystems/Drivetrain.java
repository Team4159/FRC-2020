package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private TalonSRX left_front_talon, left_rear_talon, right_front_talon, right_rear_talon;

  private SpeedControllerGroup left_talons;
  private SpeedControllerGroup right_talons;

  private DifferentialDrive differential_drive;
  private DifferentialDriveOdometry odometry;

  private PigeonIMU pigeon;

  public Drivetrain() {
    left_front_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_FRONT_TALON));
    left_rear_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_REAR_TALON));
    right_front_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_FRONT_TALON));
    right_rear_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_REAR_TALON));

    left_front_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    right_front_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    resetEncoders();

    left_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) left_front_talon,
            (WPI_TalonSRX) left_rear_talon);
    right_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) right_front_talon,
            (WPI_TalonSRX) right_rear_talon);

    left_talons.setInverted(true);

    pigeon = new PigeonIMU(right_rear_talon);

    resetDirection();

    differential_drive = new DifferentialDrive(left_talons, right_talons);
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getDirection()));
  }

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  @Override
  public void periodic() {
    odometry.update(
            Rotation2d.fromDegrees(getDirection()),
            getLeftDistance(),
            getRightDistance());
  }

  public void arcadeDrive(double forward, double rotation) {
    differential_drive.arcadeDrive(forward, rotation);
  }

  public void rawDrive(double left, double right) {
    left_talons.set(left);
    right_talons.set(right);
  }

  public void voltsDrive(double left_volts, double right_volts) {
    left_talons.setVoltage(left_volts);
    right_talons.setVoltage(right_volts);
  }

  public double getLeftVoltage() {
    return left_front_talon.getMotorOutputVoltage();
  }

  public double getRightVoltage() {
    return right_front_talon.getMotorOutputVoltage();
  }

  public void setPose(Pose2d pose) {
    resetEncoders();
    odometry.resetPosition(pose, Rotation2d.fromDegrees(getDirection()));
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
  }

  public void resetEncoders() {
    left_front_talon.setSelectedSensorPosition(0);
    right_front_talon.setSelectedSensorPosition(0);
  }

  // distance in meters
  public double getLeftDistance() {
    return left_front_talon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  // velocity in meters / sec
  public double getLeftVelocity() {
    return left_front_talon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public double getRightDistance() {
    return right_front_talon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public double getRightVelocity() {
    return right_front_talon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public void resetDirection() {
    pigeon.setYaw(0.0);
  }

  public double getDirection() {
    return pigeon.getFusedHeading();
  }

  public double getAngularVelocity() {
    double[] xyz_degrees_per_second = new double[3];
    pigeon.getRawGyro(xyz_degrees_per_second);

    return xyz_degrees_per_second[0];
  }
}