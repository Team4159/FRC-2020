package org.team4159.frc.robot;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_TALON = 0;
    public static final int LEFT_REAR_TALON = 1;
    public static final int RIGHT_FRONT_TALON = 2;
    public static final int RIGHT_REAR_TALON = 3;

    public static final int PIGEON_IMU = 4;
  }

  public static final class DRIVE_CONSTANTS {
    public static final int[] LEFT_ENCODER_PORTS = new int[] {0, 1};
    public static final int[] RIGHT_ENCODER_PORTS = new int[] {2, 3};

    public static final boolean LEFT_ENCODER_REVERSED = false;
    public static final boolean RIGHT_ENCODER_REVERSED = false;
    public static final double ENCODER_DIST_PER_PULSE = 1.0;
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }
}
