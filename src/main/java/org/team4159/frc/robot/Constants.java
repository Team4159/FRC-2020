package org.team4159.frc.robot;

import com.revrobotics.ColorMatch;
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
    public static final Color CONTROL_SPINNER_BLUE = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color CONTROL_SPINNER_GREEN = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color CONTROL_SPINNER_RED = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color CONTROL_SPINNER_YELLOW = ColorMatch.makeColor(0.361, 0.524, 0.113);
  }
}
