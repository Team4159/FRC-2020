package org.team4159.lib.math;

/*
* Adapted from: Team 254
* https://github.com/Team254/FRC-2018-Public/blob/master/src/main/java/com/team254/lib/util/Util.java
*/

public class Epsilon {
  public static double kEpsilon = 1E-12;

  public static boolean epsilonEquals(double a, double b, double epsilon) {
    return (a - epsilon <= b) && (a + epsilon >= b);
  }

  public static boolean epsilonEquals(double a, double b) {
    return epsilonEquals(a, b, kEpsilon);
  }
}
