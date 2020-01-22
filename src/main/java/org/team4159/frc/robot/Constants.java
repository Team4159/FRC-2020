package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.util.Units;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_FALCON_ID = 3;
    public static final int LEFT_REAR_FALCON_ID = 4;
    public static final int RIGHT_FRONT_FALCON_ID = 5;
    public static final int RIGHT_REAR_TALON_ID = 6;

    public static final int TURRET_FALCON_ID = 7;

    public static final int LEFT_SHOOTER_SPARK_ID = 1;
    public static final int RIGHT_SHOOTER_SPARK_ID = 2;

    public static final int ARM_SPARK_ID = 3;
    public static final int LIFTER_TALON_ID = 2;

    public static final int PIGEON_ID = 1;
  }

  public static final class DRIVE_CONSTANTS {
    public static final double GEAR_RATIO = 8.48;
    public static final double WHEEL_RADIUS = Units.inchesToMeters(3.0);
    public static final double WHEEL_CIRCUMFERENCE = Math.PI * 2.0 * WHEEL_RADIUS;

    public static final int FALCON_CPR = 2048;

    public static final double METERS_PER_TICK = WHEEL_CIRCUMFERENCE / (FALCON_CPR * GEAR_RATIO);

    // TODO: MEASURE!
    public static final double MAX_TRAJECTORY_SPEED = 0.5;
    public static final double MAX_TRAJECTORY_ACCELERATION = 3.0;

    public static final double kS = 1.33; // volts
    public static final double kV = 1.43; // volts * seconds / meters
    public static final double kA = 0.115; // volts * (seconds ^ 2) / meters

    public static final double kP = 0.00118;
    public static final double kD = 0.000566;

    public static final double TRACK_WIDTH = Units.inchesToMeters(20);

    // ramsete constants (tested for most robots)
    public static final double kB = 2.0;
    public static final double kZeta = 0.7;
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }

}
