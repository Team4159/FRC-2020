package org.team4159.lib.control.signal;

import edu.wpi.first.wpilibj.RobotController;

public class SignalUtil {
  private SignalUtil() {

  }

  public static double clampToPercent(double value) {
    return Math.max(-1, Math.min(value, 1));
  }

  public static double squareWithSign(double n) {
    return Math.copySign(n * n, n);
  }

  public static double voltsToPercentOutput(double volts) {
    return volts / RobotController.getBatteryVoltage();
  }
}
