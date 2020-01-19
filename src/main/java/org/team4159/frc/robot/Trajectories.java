package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.util.List;

public class Trajectories {
  public static final Trajectory TEST_TRAJECTORY =
          TrajectoryGenerator.generateTrajectory(
                  List.of(
                          new Pose2d(1.0, 0.0, Rotation2d.fromDegrees(0.0)),
                          new Pose2d(0.0, 3.0, Rotation2d.fromDegrees(90.0))
                  ),
                  new TrajectoryConfig(
                          0.5,
                          Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
                  )
          );

  public static final Trajectory TRENCH_RUN_BALL_TO_SHOOTING_POS =
          TrajectoryGenerator.generateTrajectory(
                  new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(0.0)),
                  List.of(
                          new Translation2d(Units.feetToMeters(2.0), Units.feetToMeters(2.0))
                  ),
                  new Pose2d(Units.inchesToMeters(27.75), Units.inchesToMeters(250.36), Rotation2d.fromDegrees(180.0)),
                  new TrajectoryConfig(
                          Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
                          Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
                  )
          );
}

