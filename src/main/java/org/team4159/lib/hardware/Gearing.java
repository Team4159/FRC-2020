package org.team4159.lib.hardware;

import org.team4159.lib.math.Cardinal;

public class Gearing {
  public int COUNTS_PER_REV;
  public double COUNTS_PER_DEGREE,
  COUNTS_PER_RADIAN;

  public Gearing(double gear_ratio, int encoder_cpr) {
    COUNTS_PER_REV = (int) (gear_ratio * encoder_cpr);
    COUNTS_PER_DEGREE = COUNTS_PER_REV / 360.0;
    COUNTS_PER_RADIAN = (int) (COUNTS_PER_REV / Cardinal.kTau);
  }
}
