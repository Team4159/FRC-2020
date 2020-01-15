package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.util.List;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_FALCON_ID = 0;
    public static final int LEFT_REAR_FALCON_ID = 1;
    public static final int RIGHT_FRONT_FALCON_ID = 2;
    public static final int RIGHT_REAR_TALON_ID = 3;

    public static final int PIGEON_ID = 0;
  }

  public static final class DRIVE_CONSTANTS {
    public static final double METERS_PER_TICK = 1.0; // meters / ticks
    public static final double TRACK_WIDTH = 0.69;

    // TODO: MEASURE!
    public static final double MAX_TRAJECTORY_SPEED = 3.0;
    public static final double MAX_TRAJECTORY_ACCELERATION = 3.0;

    public static final double kS = 0.22; // volts
    public static final double kV = 1.98; // volts * seconds / meters
    public static final double kA = 0.2; // volts * (seconds ^ 2) / meters

    public static final double kP = 8.5;

    // ramsete constants (tested for most robots)
    public static final double kB = 2.0;
    public static final double kZeta = 0.7;
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }

  public static final class TRAJECTORIES {
    public static final Trajectory TEST_TRAJECTORY =
            TrajectoryGenerator.generateTrajectory(
                    List.of(
                            new Pose2d(Units.feetToMeters(1.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(90.0)),
                            new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(5.0), Rotation2d.fromDegrees(90.0))
                    ),
                    new TrajectoryConfig(
                            DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
                            DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
                    )
            );

    public static final Trajectory TRENCH_RUN_BAL_TO_SHOOTING_POS =
            TrajectoryGenerator.generateTrajectory(
                    new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(0.0)),
                    List.of(
                            new Translation2d(Units.feetToMeters(2.0), Units.feetToMeters(2.0))
                    ),
                    new Pose2d(Units.inchesToMeters(27.75), Units.inchesToMeters(250.36), Rotation2d.fromDegrees(180.0)),
                    new TrajectoryConfig(
                            DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
                            DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
                    )
            );

  }
}
