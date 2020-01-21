package org.team4159.lib.units.derivatives;

import org.team4159.lib.units.Frac;
import org.team4159.lib.units.Unit;

import org.team4159.lib.units.Units;

public class Velocity {
  public static Frac of(Unit unit) {
    return unit.divide(Units.SECOND);
  }
}
