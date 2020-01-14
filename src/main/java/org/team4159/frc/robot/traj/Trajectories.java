package org.team4159.frc.robot.traj;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.util.List;

import static org.team4159.frc.robot.Constants.*;

public class Trajectories {
  private static final TrajectoryConfig config = new TrajectoryConfig(DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED, DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION);

  // Robot drives from Trench Run to Shooting Position
  public static Trajectory fromTrenchRunBalltoShootingPosition() {

    // start and end positions and headings of robot
    Pose2d trench_ball_pos = new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(0.0));
    Pose2d shooting_pos = new Pose2d(Units.inchesToMeters(27.75), Units.inchesToMeters(250.36), Rotation2d.fromDegrees(180.0));

    // maximum velocity and acceleration respectively

    // if IntelliJ doesn't recognize List.of(...), configure IntelliJ to use java 10+
    return TrajectoryGenerator.generateTrajectory(
            trench_ball_pos,
            List.of(
                    new Translation2d(Units.feetToMeters(2.0), Units.feetToMeters(2.0)) // to avoid running into Shield Generator
            ),
            shooting_pos,
            config
    );
  }

  // Robot drives 5 ft forward and 1 ft to the left
  public static Trajectory testTrajectory() {
    var initial_pos = new Pose2d(Units.feetToMeters(1.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(90.0));
    var end_pos = new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(5.0), Rotation2d.fromDegrees(90.0));

    var config = new TrajectoryConfig(3.0, 3.0);

    return TrajectoryGenerator.generateTrajectory(
            List.of(
                    initial_pos,
                    end_pos
            ),
            config
    );
  }
}