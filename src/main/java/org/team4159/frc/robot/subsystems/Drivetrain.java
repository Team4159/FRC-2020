package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.frc.robot.controllers.DrivetrainController;
import org.team4159.lib.control.signal.DriveSignal;
// If I don't do this, IntelliJ begins sobbing
import org.team4159.lib.control.signal.filters.*;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private CardinalFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;
  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDriveOdometry odometry;
  private PigeonIMU pigeon;
  private LowPassFilterSource filtered_heading;

  private DrivetrainController drivetrain_controller;

  public Drivetrain() {
    left_front_falcon = new CardinalFX(CAN_IDS.LEFT_FRONT_FALCON, NeutralMode.Brake);
    left_rear_falcon = new CardinalFX(CAN_IDS.LEFT_REAR_FALCON, NeutralMode.Brake);
    right_front_falcon = new CardinalFX(CAN_IDS.RIGHT_FRONT_FALCON, NeutralMode.Brake);
    right_rear_falcon = new CardinalFX(CAN_IDS.RIGHT_REAR_FALCON, NeutralMode.Brake);

    left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    // setSensorPhase isn't working

    left_falcons = new SpeedControllerGroup(
      left_front_falcon,
      left_rear_falcon
    );
    right_falcons = new SpeedControllerGroup(
      right_front_falcon,
      right_rear_falcon
    );

    left_falcons.setInverted(false);
    right_falcons.setInverted(true);

    pigeon = new PigeonIMU(CAN_IDS.PIGEON);

    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
    filtered_heading = new LowPassFilterSource(pigeon::getFusedHeading, 10);

    drivetrain_controller = new DrivetrainController(this);

    zeroSensors();
  }

  @Override
  public void periodic() {
    odometry.update(
      Rotation2d.fromDegrees(getDirection()),
      getLeftDistance(),
      getRightDistance()
    );
    filtered_heading.get();

    System.out.println(getLeftDistance() + ", " + getRightDistance());

    drivetrain_controller.update();
  }

  public void drive(DriveSignal signal) {
    left_falcons.set(signal.left);
    right_falcons.set(signal.right);
  }

  public void stop() {
    drive(DriveSignal.fromVolts(0.0, 0.0));
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
    return -1 * left_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  // velocity in meters / sec
  public double getLeftVelocity() {
    return -1 * left_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public double getRightDistance() {
    return right_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public double getRightVelocity() {
    return right_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public Pose2d getPose() {
    return odometry.getPoseMeters();
  }

  public DrivetrainController getController() {
    return drivetrain_controller;
  }
}
