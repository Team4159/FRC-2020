package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.util.Units;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_TALON = 0;
    public static final int LEFT_REAR_TALON = 1;
    public static final int RIGHT_FRONT_TALON = 2;
    public static final int RIGHT_REAR_TALON = 3;
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
}
