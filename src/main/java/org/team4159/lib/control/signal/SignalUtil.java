package org.team4159.lib.control.signal;

import edu.wpi.first.wpilibj.RobotController;
import org.team4159.lib.math.MathUtil;

public class SignalUtil {
  private SignalUtil() {

  }

  public static double clampToPercent(double value) {
    return MathUtil.clamp(-1, 1, value);
  }

  public static double squareWithSign(double n) {
    return Math.copySign(n * n, n);
  }

  public static double voltsToPercentOutput(double volts) {
    return volts / RobotController.getBatteryVoltage();
  }
}
