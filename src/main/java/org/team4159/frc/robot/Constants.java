package org.team4159.frc.robot;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_TALON_ID = 0;
    public static final int LEFT_REAR_TALON_ID = 1;
    public static final int RIGHT_FRONT_TALON_ID = 2;
    public static final int RIGHT_REAR_TALON_ID = 3;

    public static final int PRIMARY_SHOOTER_TALON_ID = 4;
    public static final int SECONDARY_SHOOTER_TALON_ID = 5;
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }

  public final static class SHOOTER_CONSTANTS {
    public static final double kP = 0.02;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }
}
