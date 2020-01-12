package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.ColorShim;

public final class Constants {
  public final static class CAN_IDS {
    public static final int LEFT_FRONT_TALON = 0;
    public static final int LEFT_REAR_TALON = 1;
    public static final int RIGHT_FRONT_TALON = 2;
    public static final int RIGHT_REAR_TALON = 3;
  }

  public final static class CONTROLS {
    public static final int LEFT_JOY = 0;
    public static final int RIGHT_JOY = 1;
  }

  public final static class FIELD_CONSTANTS {
    public static final Color CONTROL_SPINNER_BLUE = new ColorShim(0, 1, 1);
    public static final Color CONTROL_SPINNER_GREEN = new ColorShim(0, 1, 0);
    public static final Color CONTROL_SPINNER_RED = new ColorShim(1, 0, 0);
    public static final Color CONTROL_SPINNER_YELLOW = new ColorShim(1, 1, 0);
  }
}
