package org.team4159.lib.math;

public class MathUtil {
  public static double kTau = Math.PI * 2;

  public static double clamp(double low, double high, double a) {
    return Math.max(low, Math.min(a, high));
  }

  /*
   * Adapted from: Team 254
   * https://github.com/Team254/FRC-2018-Public/blob/master/src/main/java/com/team254/lib/util/Util.java
   */

  public static double kEpsilon = 1E-12;

  public static boolean epsilonEquals(double a, double b, double epsilon) {
    return (a - epsilon <= b) && (a + epsilon >= b);
  }

  public static boolean epsilonEquals(double a, double b) {
    return epsilonEquals(a, b, kEpsilon);
  }

  public static double epsilonRound(double n, double epsilon) {
    return Math.abs(n) <= epsilon ? 0 : n;
  }

  public static double epsilonRound(double n) {
    return epsilonRound(n, kEpsilon);
  }
}
