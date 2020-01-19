package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.team4159.lib.Odometry;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private TalonFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;

  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDrive differential_drive;

  private Odometry odometry;

  private PigeonIMU pigeon;

  private boolean is_oriented_forward;

  public Drivetrain() {
    is_oriented_forward = true;

    left_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_FRONT_FALCON_ID));
    left_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_REAR_FALCON_ID));
    right_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_FRONT_FALCON_ID));
    right_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_REAR_TALON_ID));

    left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

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

    odometry = new Odometry(new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(getDirection())));
  }

  public void flipOrientation() {
    is_oriented_forward = !is_oriented_forward;
  }

  @Override
  public void periodic() {
    odometry.update(
      getLeftDistance(),
      getRightDistance(),
      getDirection());

    SmartDashboard.putNumber("X: ", odometry.getPose().getTranslation().getX());
    SmartDashboard.putNumber("Y: ", odometry.getPose().getTranslation().getY());
    SmartDashboard.putNumber("DEG: ", + getDirection());
    SmartDashboard.putNumber("ODO_DEG: ", + odometry.getPose().getRotation().getDegrees());
  }

  private TalonFX configureTalonFX(TalonFX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  public void setRawSpeeds(double left, double right) {
    if (is_oriented_forward) {
      differential_drive.tankDrive(left, right);
    } else {
      differential_drive.tankDrive(-right, -left);
    }
  }

  public void arcadeDrive(double forward, double rotation) {
    differential_drive.arcadeDrive(forward, rotation);
  }

  public void rawDrive(double left, double right) {
    left_falcons.set(left);
    right_falcons.set(right);
  }

  public void voltsDrive(double left_volts, double right_volts) {
    left_falcons.setVoltage(left_volts);
    right_falcons.setVoltage(-right_volts);
  }

  public void stopMotors() {
    differential_drive.stopMotor();
  }

  public double getLeftVoltage() {
    return left_front_falcon.getMotorOutputVoltage();
  }

  public double getRightVoltage() {
    return right_front_falcon.getMotorOutputVoltage();
  }

  public void setPose(Translation2d coords) {
    resetEncoders();
    odometry.set(new Pose2d(coords, Rotation2d.fromDegrees(getDirection())));
  }

  public Pose2d getPose() {
    return odometry.getPose();
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

  public void zeroSensors() {
    resetEncoders();
    resetDirection();
    setPose(new Translation2d(0.0, 0.0));
  }
}