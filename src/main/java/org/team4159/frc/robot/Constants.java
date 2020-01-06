package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_TALON = 0;
    public static final int LEFT_REAR_TALON = 1;
    public static final int RIGHT_FRONT_TALON = 2;
    public static final int RIGHT_REAR_TALON = 3;

    public static final int PIGEON_IMU = 4;
  }

  public static final class DRIVE_CONSTANTS {
    public static final double METERS_PER_TICK = 1.0; // meters / ticks
    public static final double TRACK_WIDTH = 0.69;

    // TODO: MEASURE!

    public static final double kS = 0.22; // volts
    public static final double kV = 1.98; // volts * seconds / meters
    public static final double kA = 0.2; // volts * (seconds ^ 2) / meters

    public static final double kP = 8.5;

    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(TRACK_WIDTH);
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }
}
