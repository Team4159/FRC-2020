package org.team4159.frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;

import org.team4159.lib.hardware.Gearing;
import org.team4159.lib.hardware.joystick.T16000M;
import org.team4159.lib.math.MathUtil;
import org.team4159.lib.simulation.MotorModels;

public final class Constants {
  // not sure where to put these
  public static final class ENCODERS {
    public static final int FALCON_CPR = 2048;
    public static final int MAG_ENCODER_CPR = 4096;
    public static final int THROUGH_BORE_ENCODER_CPR = 2048;
  }

  public static final class CONTROLS {
    public static final class LEFT_JOY {
      public static final int USB_PORT = 0;

      public static final class BUTTON_IDS {
        public static final int FLIP_ROBOT_ORIENTATION = T16000M.TRIGGER_ID;
      }
    }

    public static final class RIGHT_JOY {
      public static final int USB_PORT = 1;

      public static final class BUTTON_IDS {
        public static final int FLIP_ROBOT_ORIENTATION = T16000M.TRIGGER_ID;
      }
    }

    public static final class SECONDARY_JOY {
      public static final int USB_PORT = 2;

      public static final class BUTTON_IDS {
        public static final int
          TOGGLE_ARM = T16000M.PRIMARY_TOP_OUTER_BTN_ID,
          INTAKE = T16000M.PRIMARY_TOP_MIDDLE_BTN_ID,
          RUN_INTAKE = T16000M.PRIMARY_TOP_MIDDLE_BTN_ID,
          LIMELIGHT_SEEK = T16000M.TOP_MIDDLE_BTN_ID,
          RUN_FEEDER = T16000M.PRIMARY_BOTTOM_MIDDLE_BTN_ID,
          RUN_SHOOTER = T16000M.TRIGGER_ID,
          RUN_NECK = T16000M.PRIMARY_BOTTOM_INNER_BTN_ID;
      }
    }
  }

  public static final class CAN_IDS {
    public static final int LEFT_FRONT_FALCON = 2;
    public static final int LEFT_REAR_FALCON = 3;
    public static final int RIGHT_FRONT_FALCON = 0;
    public static final int RIGHT_REAR_FALCON = 1;
    public static final int TURRET_FALCON = 8; // unknown

    public static final int SHOOTER_SPARK_ONE = 3;
    public static final int SHOOTER_SPARK_TWO = 4;

    public static final int ARM_SPARK = 6;
    public static final int INTAKE_SPARK = 2; // unknown
    // TODO: Change!
    public static final int LOWER_FEEDER_TALON = 4;
    public static final int UPPER_FEEDER_SPARK = 1;
    public static final int NECK_SPARK = 5;

    public static final int PIGEON = 0;
  }

  public static final class DRIVE_CONSTANTS {
    public static final Gearing GEARING = new Gearing(8.48, ENCODERS.FALCON_CPR);
    public static final double WHEEL_RADIUS = Units.inchesToMeters(3.0);
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_RADIUS * MathUtil.kTau;

    public static final double METERS_PER_COUNT = WHEEL_CIRCUMFERENCE / GEARING.COUNTS_PER_REV;

    public static final boolean IS_GYRO_INVERTED = false;

    // TODO: Find
    public static final double MAX_TRAJECTORY_SPEED = 2.0;
    public static final double MAX_TRAJECTORY_ACCELERATION = 3.0;
    public static final double MAX_TRAJECTORY_VOLTAGE = 10.0;

    public static final double kS = 0.717; // volts
    public static final double kV = 4.85; // volts * seconds / meters
    public static final double kA = 0.37; // volts * (seconds ^ 2) / meters

    public static final double kP = 0.0;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double TRACK_WIDTH = 0.5461;

    // ramsete constants (tested for most robots)
    public static final double kB = 2.0;
    public static final double kZeta = 0.7;
  }

  public static final class SHOOTER_CONSTANTS {
    public static final double COUNTS_PER_SECOND_TO_RPM = (1.0 / ENCODERS.THROUGH_BORE_ENCODER_CPR) * 60;
    public static final double RPM_TO_COUNTS_PER_SECOND = 1.0 / COUNTS_PER_SECOND_TO_RPM;

    public static final double ACCEPTABLE_SPEED_ERROR = 5;
    public static final double MAX_SPEED = Units.radiansPerSecondToRotationsPerMinute(MotorModels.NEO.free_speed) * 30 / 20;

    public static final int ENCODER_CHANNEL_A_PORT = 4;
    public static final int ENCODER_CHANNEL_B_PORT = 5;
    public static final boolean IS_ENCODER_REVERSED = true;
    public static final EncodingType ENCODER_ENCODING_TYPE = EncodingType.k4X;

    public static final double kP = 0.1;
    public static final double kI = 0.0;
    public static final double kD = 0.0;
  }

  public static final class ARM_CONSTANTS {
    public static final Gearing GEARING = new Gearing(1.0, ENCODERS.MAG_ENCODER_CPR);
    public static final int ACCEPTABLE_ERROR_IN_DEGREES = 3;

    // TODO: Find
    public static final int RANGE_IN_COUNTS = 435;
    public static final int ACCEPTABLE_ERROR_IN_COUNTS = (int) (ACCEPTABLE_ERROR_IN_DEGREES * GEARING.COUNTS_PER_DEGREE);

    public static final int UP_POSITION = 0;
    public static final int DOWN_POSITION = RANGE_IN_COUNTS;

    public static final int LIMIT_SWITCH_PORT = 0;

    public static final int ENCODER_CHANNEL_A_PORT = 9;
    public static final int ENCODER_CHANNEL_B_PORT = 8;
    public static final boolean IS_ENCODER_REVERSED = false;
    public static final EncodingType ENCODER_ENCODING_TYPE = EncodingType.k4X;

    public static final double kP = 5.5 / RANGE_IN_COUNTS;
    public static final double kI = 0.0;
    public static final double kD = 0.0;

    public static final double ZEROING_SPEED = 0.3;
  }

  public static final class TURRET_CONSTANTS {
    public static final Gearing GEARING = new Gearing(124.0 / 16.0, ENCODERS.FALCON_CPR);
    public static final int RANGE_IN_DEGREES = 240;

    // Range measurement trials:
    // T1A = 4150+5340
    // T1B = 4150+5518
    // T2A = 4150+5348
    // T2B = 4150+5226
    // T3A = 4150+5434
    // T3B = 4150+5482

    // Avg = 8627.66

    public static final int FORWARD_POSITION = 5196 / 2;
    public static final int REVERSE_POSITION = -1 * FORWARD_POSITION;

    // Somewhat arbitrary
    public static final int INITIAL_SEEKING_RANGE = 512;
    public static final int SEEKING_RANGE_INCREMENT = 512;

    public static final int BUFFER = 300;

    public static final int SAFE_FORWARD_POSITION = FORWARD_POSITION - BUFFER;
    public static final int SAFE_REVERSE_POSITION = REVERSE_POSITION + BUFFER;

    public static final int CENTER_POSITION = 0;

    public static final double POSITION_kP = 1.0 / 5196 / 2;
    public static final double POSITION_kI = 0;
    public static final double POSITION_kD = 0.00001;

    public static final double LIMELIGHT_AIM_kP = -0.01;
    public static final double LIMELIGHT_AIM_kI = 0;
    public static final double LIMELIGHT_AIM_kD = -0.005;

    public static final double LIMELIGHT_TURN_TOLERANCE = 1;

    public static final double ZEROING_SPEED = 0.075;
    public static final double SEEKING_SPEED = 0.1;
  }

  public static final class LIMELIGHT_CONSTANTS {
    // TEMPORARY VALUES FOR TESTING SETUP

    public static final double MOUNT_HEIGHT = 36.25;
    public static final double MOUNT_ANGLE = 25;

    public static final double VISION_TARGET_HEIGHT = 8 * 12 + 2.25 - MOUNT_HEIGHT;
  }

  public static final class INTAKE_CONSTANTS {
    public static final double INTAKE_SPEED = 1;
  }

  public static final class FEEDER_CONSTANTS {
    public static final double FLOOR_FEEDING_SPEED = 1;
    public static final double TOWER_FEEDING_SPEED = 0.3;
  }

  public static final class FIELD_CONSTANTS {
    public static final Color CONTROL_SPINNER_BLUE = ColorMatch.makeColor(0.143, 0.427, 0.429);
    public static final Color CONTROL_SPINNER_GREEN = ColorMatch.makeColor(0.197, 0.561, 0.240);
    public static final Color CONTROL_SPINNER_RED = ColorMatch.makeColor(0.561, 0.232, 0.114);
    public static final Color CONTROL_SPINNER_YELLOW = ColorMatch.makeColor(0.361, 0.524, 0.113);
  }
}
