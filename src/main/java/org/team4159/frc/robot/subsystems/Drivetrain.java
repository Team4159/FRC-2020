package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private TalonFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;

  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDrive differential_drive;
  private DifferentialDriveOdometry odometry;

  private PigeonIMU pigeon;

  public Drivetrain() {
    left_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_FRONT_FALCON_ID));
    left_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_REAR_FALCON_ID));
    right_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_FRONT_FALCON_ID));
    right_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_REAR_TALON_ID));

    left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);
    right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

    //left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    //right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    resetEncoders();

    left_falcons = new SpeedControllerGroup(
            (WPI_TalonFX) left_front_falcon,
            (WPI_TalonFX) left_rear_falcon);
    right_falcons = new SpeedControllerGroup(
            (WPI_TalonFX) right_front_falcon,
            (WPI_TalonFX) right_rear_falcon);

    left_falcons.setInverted(true);

    pigeon = new PigeonIMU(CAN_IDS.PIGEON_ID);

    resetDirection();

    differential_drive = new DifferentialDrive(left_falcons, right_falcons);
    odometry = new DifferentialDriveOdometry(Rotation2d.fromDegrees(getDirection()));
  }

  private TalonFX configureTalonFX(TalonFX talonSRX) {
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

  public void stopMotors() {
    differential_drive.stopMotor();
  }

  public void arcadeDrive(double forward, double rotation) {
    differential_drive.arcadeDrive(forward, rotation);
  }

  public void tankDrive(double left, double right) {
    differential_drive.tankDrive(left, right);
  }

  public void rawDrive(double left, double right) {
    left_falcons.set(left);
    right_falcons.set(right);
  }

  public void voltsDrive(double left_volts, double right_volts) {
    left_falcons.setVoltage(left_volts);
    right_falcons.setVoltage(right_volts);
  }

  public double getLeftVoltage() {
    return left_front_falcon.getMotorOutputVoltage();
  }

  public double getRightVoltage() {
    return right_front_falcon.getMotorOutputVoltage();
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
    left_front_falcon.setSelectedSensorPosition(0);
    right_front_falcon.setSelectedSensorPosition(0);
  }

  // distance in meters
  public double getLeftDistance() {
    return left_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  // velocity in meters / sec
  public double getLeftVelocity() {
    return left_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public double getRightDistance() {
    return right_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public double getRightVelocity() {
    return right_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public void resetDirection() {
    pigeon.setFusedHeading(0.0);
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