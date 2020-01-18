package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.util.Units;

import java.util.List;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_FALCON_ID = 3;
    public static final int LEFT_REAR_FALCON_ID = 4;
    public static final int RIGHT_FRONT_FALCON_ID = 5;
    public static final int RIGHT_REAR_TALON_ID = 6;

    public static final int TURRET_FALCON_ID = 7;

    public static final int LEFT_SHOOTER_SPARK_ID = 1;
    public static final int RIGHT_SHOOTER_SPARK_ID = 2;

    public static final int INTAKE_TALON_ID = 1;
    public static final int LIFTER_TALON_ID = 2;

    public static final int PIGEON_ID = 1;
  }

  public static final class DRIVE_CONSTANTS {
    public static final double GEAR_RATIO = 8.48;
    public static final double WHEEL_RADIUS = Units.inchesToMeters(3.0);

    public static final int FALCON_CPR = 2048;

    public static final double METERS_PER_TICK = (Math.PI * 2.0 * WHEEL_RADIUS) / (FALCON_CPR * GEAR_RATIO);

    public static final double TRACK_WIDTH = Units.inchesToMeters(21.5);

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

}
