package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.frc.robot.controllers.TrajectoryController;
import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.lib.control.signal.filters.LowPassFilterSource;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private enum State {
    PATH_FOLLOWING,
    OPEN_LOOP
  }
  private State state = State.OPEN_LOOP;

  private TalonFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;
  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDriveOdometry odometry;
  private PigeonIMU pigeon;
  private LowPassFilterSource filtered_heading;

  private TrajectoryController trajectory_controller;
  private DriveSignal drive_signal;
  private boolean is_oriented_forward = true;

  public Drivetrain() {
    left_front_falcon = new CardinalFX(CAN_IDS.LEFT_FRONT_FALCON, NeutralMode.Coast);
    left_rear_falcon = new CardinalFX(CAN_IDS.LEFT_REAR_FALCON, NeutralMode.Coast);
    right_front_falcon = new CardinalFX(CAN_IDS.RIGHT_FRONT_FALCON, NeutralMode.Coast);
    right_rear_falcon = new CardinalFX(CAN_IDS.RIGHT_REAR_FALCON, NeutralMode.Coast);

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
    right_falcons.setInverted(false);

    pigeon = new PigeonIMU(CAN_IDS.PIGEON);

    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
    filtered_heading = new LowPassFilterSource(pigeon::getFusedHeading, 10);

    trajectory_controller  = new TrajectoryController(this);

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

    switch (state) {
      case PATH_FOLLOWING:
        drive_signal = trajectory_controller.update();
        if (trajectory_controller.isIdle()) {
          state = State.OPEN_LOOP;
        }
      case OPEN_LOOP:
        DriveSignal filtered_signal = drive_signal;
        if (is_oriented_forward) {
          filtered_signal = drive_signal.invert();
        }
        left_falcons.set(filtered_signal.left);
        right_falcons.set(filtered_signal.right);
        break;
    }
  }

  public void drive(DriveSignal drive_signal) {
    state = State.OPEN_LOOP;
    this.drive_signal = drive_signal;
  }

  public void followTrajectory(Trajectory trajectory) {
    state = State.PATH_FOLLOWING;
    trajectory_controller.setTrajectory(trajectory);
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

    System.out.println(getDirection());
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
}
