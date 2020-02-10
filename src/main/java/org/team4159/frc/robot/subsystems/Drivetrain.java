package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.lib.control.signal.filters.LowPassFilterSource;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private TalonFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;
  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDriveOdometry odometry;
  private PigeonIMU pigeon;
  private LowPassFilterSource filtered_heading;

  private boolean is_oriented_forward = true;

  public Drivetrain() {
    left_front_falcon = new CardinalFX(CAN_IDS.LEFT_FRONT_FALCON_ID, NeutralMode.Coast);
    left_rear_falcon = new CardinalFX(CAN_IDS.LEFT_REAR_FALCON_ID, NeutralMode.Coast);
    right_front_falcon = new CardinalFX(CAN_IDS.RIGHT_FRONT_FALCON_ID, NeutralMode.Coast);
    right_rear_falcon = new CardinalFX(CAN_IDS.RIGHT_REAR_FALCON_ID, NeutralMode.Coast);

    left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    // setSensorPhase isn't working

    left_falcons = new SpeedControllerGroup(
      (WPI_TalonFX) left_front_falcon,
      (WPI_TalonFX) left_rear_falcon);
    right_falcons = new SpeedControllerGroup(
      (WPI_TalonFX) right_front_falcon,
      (WPI_TalonFX) right_rear_falcon);
    left_falcons.setInverted(true);
    right_falcons.setInverted(true);

    pigeon = new PigeonIMU(CAN_IDS.PIGEON_ID);

    odometry = new DifferentialDriveOdometry(new Rotation2d(0));

    zeroSensors();
  }

  public void flipOrientation() {
    is_oriented_forward = !is_oriented_forward;
  }

  @Override
  public void periodic() {
    odometry.update(
      Rotation2d.fromDegrees(getDirection()),
      getLeftDistance(),
      getRightDistance()
    );
    filtered_heading.get();

    SmartDashboard.putNumber("X", getPose().getTranslation().getX());
    SmartDashboard.putNumber("Y", getPose().getTranslation().getY());
    SmartDashboard.putNumber("Angle", getDirection());
    SmartDashboard.putNumber("Left Encoder", getLeftDistance());
    SmartDashboard.putNumber("Right Encoder", getRightDistance());
  }

  public void rawDrive(DriveSignal signal) {
    if (is_oriented_forward) {
      signal.invert();
    }

    left_falcons.set(signal.left);
    right_falcons.set(signal.right);
  }

  public void tankDrive(double left, double right) {
    rawDrive(new DriveSignal(left, right, true));
  }

  public void arcadeDrive(double speed, double turn) {
    rawDrive(DriveSignal.fromArcade(speed, turn, true));
  }

  public void voltsDrive(double left_volts, double right_volts) {
    rawDrive(DriveSignal.fromVolts(left_volts, right_volts));
  }

  public void stop() {
    rawDrive(DriveSignal.NEUTRAL);
  }

  public void resetEncoders() {
    left_front_falcon.setSelectedSensorPosition(0);
    right_front_falcon.setSelectedSensorPosition(0);
  }

  public void resetDirection() {
    pigeon.setFusedHeading(0);
  }

  public void zeroSensors() {
    resetEncoders();
    resetDirection();
    odometry.resetPosition(
      new Pose2d(new Translation2d(0, 0), Rotation2d.fromDegrees(0)),
      Rotation2d.fromDegrees(0)
    );
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
  }

  public double getDirection() {
    return Math.IEEEremainder(pigeon.getFusedHeading(), 360) * (DRIVE_CONSTANTS.IS_GYRO_INVERTED ? -1 : 1);
  }

  public double getLeftVoltage() {
    return left_front_falcon.getMotorOutputVoltage();
  }

  public double getRightVoltage() {
    return right_front_falcon.getMotorOutputVoltage();
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
    return -1 * right_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public double getRightVelocity() {
    return -1 * right_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_TICK;
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }
}
