package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;

import java.util.List;

/*
 * http://docs.wpilib.org/en/latest/docs/software/advanced-control/trajectories/index.html
 */
public class Trajectories {
  public static final Trajectory TURN_RIGHT_90_GO_FORWARD_ONE_METER =
     TrajectoryGenerator.generateTrajectory(
       new Pose2d(0, 0, new Rotation2d(0)),
       List.of(
       ),
       new Pose2d(0, 1, new Rotation2d(0)),
       new TrajectoryConfig(
         Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
         Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
       )
     );

  public static final Trajectory GO_FORWARD_ONE_METER =
    TrajectoryGenerator.generateTrajectory(
      List.of(
        new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
        new Pose2d(1, 0, Rotation2d.fromDegrees(0))
        ),
      new TrajectoryConfig(
        Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
        Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
      )
    );

  public static final Trajectory TEST_3 =
    TrajectoryGenerator.generateTrajectory(
      new Pose2d(0, 0, new Rotation2d(0)),
      List.of(
      ),
      new Pose2d(1, 1, new Rotation2d(0)),
      new TrajectoryConfig(
        Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
        Constants.DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
      )
    );
}
