package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.util.Units;

import static org.team4159.frc.robot.Constants.*;

import java.io.IOException;
import java.util.List;

/*
 * http://docs.wpilib.org/en/latest/docs/software/advanced-control/trajectories/index.html
 */
public class Trajectories {
  private static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH);
  private static final DifferentialDriveVoltageConstraint constraint = new DifferentialDriveVoltageConstraint(
    new SimpleMotorFeedforward(
      DRIVE_CONSTANTS.kS,
      DRIVE_CONSTANTS.kV,
      DRIVE_CONSTANTS.kA),
    kinematics,
    DRIVE_CONSTANTS.MAX_TRAJECTORY_VOLTAGE
  );

  private static final TrajectoryConfig config = new TrajectoryConfig(
    DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
    DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
  )
    .setKinematics(kinematics)
    .addConstraint(constraint);

  public Trajectory SLALOM_TRAJECTORY = new Trajectory();

  public void loadTrajectories() {
    SLALOM_TRAJECTORY = loadTrajectory("Slalom.wpilib.json");
  }

  public Trajectory loadTrajectory(String path) {
    try {
      return TrajectoryUtil.fromPathweaverJson(Filesystem.getDeployDirectory().toPath().resolve("output/" + path));
    } catch (IOException ex) {
      DriverStation.reportError("Unable to open trajectory" + path, ex.getStackTrace());
      return new Trajectory();
    }
  }
}