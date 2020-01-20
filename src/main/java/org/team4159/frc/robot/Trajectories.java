package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

import java.util.List;

/*
 * http://docs.wpilib.org/en/latest/docs/software/advanced-control/trajectories/index.html
 */
public class Trajectories {
  public static final Trajectory TEST_TRAJECTORY =
    TrajectoryGenerator.generateTrajectory(
      new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0)),
      List.of(),
      new Pose2d(0.0, 1.0, Rotation2d.fromDegrees(0.0)),
      new TrajectoryConfig(
        0.5,
        Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
      )
    );
}
