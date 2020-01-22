package org.team4159.lib.math.units;

/*
* Inspired by https://github.com/team5419/fault/tree/master/src/main/kotlin/org/team5419/fault/math/units
*/

import org.team4159.lib.math.units.derivatives.Acceleration;
import org.team4159.lib.math.units.derivatives.Jerk;

public class Units {
  public static final Unit UNIT = new Unit();

  public static final Unit SECOND = new Second();
  public static final Unit KILOGRAM = new Kilogram();
  public static final Unit METER = new Meter();
  public static final Unit AMPERE = new Ampere();

  // derived
  public static final Unit NEWTON = Acceleration.of(KILOGRAM.mult(METER));
  public static final Unit VOLT = Jerk.of(KILOGRAM.mult(METER).mult(METER).div(AMPERE));
}
