package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.util.Units;

import org.team4159.lib.math.Baba;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_FALCON_ID = 1;
    public static final int LEFT_REAR_FALCON_ID = 0;
    public static final int RIGHT_FRONT_FALCON_ID = 2;
    public static final int RIGHT_REAR_TALON_ID = 3;
    public static final int TURRET_FALCON_ID = 9;

    public static final int FEEDER_TALON_ONE_ID = 7;
    public static final int FEEDER_TALON_TWO_ID = 50; // unknown
    public static final int INTAKE_TALON_ID = 50; // unknown
    public static final int NECK_TALON_ONE_ID = 5;
    public static final int NECK_TALON_TWO_ID = 8;
    public static final int NECK_TALON_THREE_ID = 6;

    public static final int LEFT_SHOOTER_SPARK_ID = 1;
    public static final int RIGHT_SHOOTER_SPARK_ID = 2;
    public static final int ARM_SPARK_ID = 3;

    public static final int PIGEON_ID = 0;
  }

  public static final class DRIVE_CONSTANTS {
    public static final double GEAR_RATIO = 8.48;
    public static final double WHEEL_RADIUS = Units.inchesToMeters(3.0);
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * Baba.kTau;

    public static final int FALCON_CPR = 2048;

    public static final double METERS_PER_TICK = WHEEL_CIRCUMFERENCE / (FALCON_CPR * GEAR_RATIO);

    // TODO: Tune
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
    public static final int SECONDARY_JOY = 2;
  }

  public final static class SHOOTER_CONSTANTS {
    public static final double kP = 0.02;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }

  public final static class ARM_CONSTANTS {
    public static final int LIMIT_SWITCH_PORT = 0;

    public static final double kP = 1.0;
    public static final double kI = 0.0;
    public static final double kD = 20.0;

    // TODO: Find
    public static final int UP_POSITION = 0;
    public static final int DOWN_POSITION = -2000;

    public static final double ZEROING_SPEED = 0.1;
  }
}
