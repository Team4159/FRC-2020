package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;

import static org.team4159.frc.robot.Constants.*;

import java.util.List;

/*
 * http://docs.wpilib.org/en/latest/docs/software/advanced-control/trajectories/index.html
 */
public class Trajectories {
  private static DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH);
  private static DifferentialDriveVoltageConstraint constraint = new DifferentialDriveVoltageConstraint(
    new SimpleMotorFeedforward(
      DRIVE_CONSTANTS.kS,
      DRIVE_CONSTANTS.kV,
      DRIVE_CONSTANTS.kA),
    kinematics,
    DRIVE_CONSTANTS.MAX_TRAJECTORY_VOLTAGE
  );

  private static TrajectoryConfig config = new TrajectoryConfig(
    DRIVE_CONSTANTS.MAX_TRAJECTORY_SPEED,
    DRIVE_CONSTANTS.MAX_TRAJECTORY_ACCELERATION
  )
    .setKinematics(kinematics)
    .addConstraint(constraint);

  public static final Trajectory GO_FORWARD_ONE_METER =
    TrajectoryGenerator.generateTrajectory(
      List.of(
        new Pose2d(0, 0, Rotation2d.fromDegrees(0)),
        new Pose2d(12, 6, Rotation2d.fromDegrees(0))
      ),
      config
    );
}
