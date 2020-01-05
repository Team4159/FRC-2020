package org.team4159.frc.robot.traj;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.util.ArrayList;

public class Trajectories {
  public static Trajectory generateTrajectoryFromTrenchRunBalltoShootingPosition() {

    // start and end positions and headings of robot
    var trench_ball_pos = new Pose2d(Units.feetToMeters(4.0), Units.feetToMeters(0.0), Rotation2d.fromDegrees(0.0));
    var shooting_pos = new Pose2d(Units.feetToMeters(0.0), Units.feetToMeters(15.0), Rotation2d.fromDegrees(0.0));

    // points on trajectory
    var interior_waypoints = new ArrayList<Translation2d>();
    interior_waypoints.add(new Translation2d(Units.feetToMeters(2.0), Units.feetToMeters(2.0))); // to avoid hitting shield gen

    // maximum velocity and acceleration respectively
    TrajectoryConfig config = new TrajectoryConfig(3.0, 3.0);

    return TrajectoryGenerator.generateTrajectory(
            trench_ball_pos,
            interior_waypoints,
            shooting_pos,
            config
    );
  }
}
