package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.Robot;
import org.team4159.frc.robot.controllers.DrivetrainController;
import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.lib.control.signal.filters.LowPassFilterSource;
import org.team4159.lib.hardware.controller.ctre.CardinalFX;

// Unnecessary when https://www.chiefdelphi.com/t/edu-wpi-first-wpilibj-simulation-cannot-be-imported/383522/2 fixed by wpilib
import org.team4159.lib.math.physics.DCMotorModelSim;
import org.team4159.lib.math.physics.TankDriveModel;
import org.team4159.lib.simulation.Field2d;
import org.team4159.lib.simulation.MotorModels;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private CardinalFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;
  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private DifferentialDriveOdometry odometry;
  private PigeonIMU pigeon;
  private LowPassFilterSource filtered_heading;

  private DrivetrainController drivetrain_controller;

  private final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(Constants.DRIVE_CONSTANTS.TRACK_WIDTH);
  private Field2d field2d = new Field2d();
  private double sim_left_meters = 0, sim_right_meters = 0, sim_direction = 0;
  private DifferentialDriveWheelSpeeds sim_wheel_speeds = new DifferentialDriveWheelSpeeds(0, 0);

  private Double sim_last_ts;
  // TODO: Implement this
  private Pose2d sim_pose = new Pose2d(0, 0, new Rotation2d(0));

  private TankDriveModel tank_drive_sim;

  public Drivetrain() {
    if (Robot.isSimulation()) {
      field2d.setRobotPose(sim_pose);

      tank_drive_sim = new TankDriveModel(
          55.0, // TODO: find mass
          Constants.DRIVE_CONSTANTS.TRACK_WIDTH,
          2.0,
          2.0,
          new DCMotorModelSim(MotorModels.Falcon_500),
          new DCMotorModelSim(MotorModels.Falcon_500)
      );
    }

    if (Robot.isReal()) {
      left_front_falcon = new CardinalFX(CAN_IDS.LEFT_FRONT_FALCON, NeutralMode.Coast);
      left_rear_falcon = new CardinalFX(CAN_IDS.LEFT_REAR_FALCON, NeutralMode.Coast);
      right_front_falcon = new CardinalFX(CAN_IDS.RIGHT_FRONT_FALCON, NeutralMode.Coast);
      right_rear_falcon = new CardinalFX(CAN_IDS.RIGHT_REAR_FALCON, NeutralMode.Coast);

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
      filtered_heading = new LowPassFilterSource(pigeon::getFusedHeading, 10);
    }

    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
    drivetrain_controller = new DrivetrainController(this);

    if (Robot.isReal()) {
      zeroSensors();
    }
  }

  @Override
  public void periodic() {
    if (Robot.isReal()) {
      odometry.update(
          Rotation2d.fromDegrees(getDirection()),
          getLeftDistance(),
          getRightDistance()
      );
      filtered_heading.get();
    }

    drivetrain_controller.update();
  }

  public void drive(DriveSignal signal) {
    if (Robot.isSimulation()) {
      if (sim_last_ts == null) {
        sim_last_ts = Timer.getFPGATimestamp();
        return;
      }

      double dt = Timer.getFPGATimestamp() - sim_last_ts;
      Transform2d transform = tank_drive_sim.calculate(signal, dt);

      sim_pose = sim_pose.plus(transform);
      field2d.setRobotPose(sim_pose);
    } else {
      left_falcons.set(signal.left);
      right_falcons.set(signal.right);
    }
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
    if (Robot.isSimulation()) {
      return sim_wheel_speeds;
    }

    return new DifferentialDriveWheelSpeeds(getLeftVelocity(), getRightVelocity());
  }

  public double getDirection() {
    if (Robot.isSimulation()) {
      return sim_direction;
    }

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
    if (Robot.isSimulation()) {
      return sim_left_meters;
    }

    return -1 * left_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  // velocity in meters / sec
  public double getLeftVelocity() {
    return -1 * left_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public double getRightDistance() {
    if (Robot.isSimulation()) {
      return sim_right_meters;
    }

    return right_front_falcon.getSelectedSensorPosition() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public double getRightVelocity() {
    return right_front_falcon.getSelectedSensorVelocity() * DRIVE_CONSTANTS.METERS_PER_COUNT;
  }

  public Pose2d getPose() {
    if (Robot.isSimulation()) {
      return sim_pose;
    }

    return odometry.getPoseMeters();
  }

  public DrivetrainController getController() {
    return drivetrain_controller;
  }
}
