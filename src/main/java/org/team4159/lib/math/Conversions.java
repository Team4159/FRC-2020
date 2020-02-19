package org.team4159.lib.math;

public class Conversions {
  private static double POUNDS_PER_KILOGRAM = 2.205;

  public static double poundsToKilograms(double pounds) {
    return pounds / POUNDS_PER_KILOGRAM;
  }
}
