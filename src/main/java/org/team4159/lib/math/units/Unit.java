package org.team4159.lib.math.units;

import java.util.Map;

public class Unit {
  public String symbol() {
    return "ul";
  }

  public Unit mult(Unit unit) {
    if (unit instanceof MultiUnit) {
      return unit.mult(this);
    } else {
      return new MultiUnit(Map.of(this, 1, unit, 1));
    }
  }

  public Unit div(Unit unit) {
    return mult(unit.inv());
  }

  public Unit inv() {
    return new MultiUnit(Map.of(this, -1));
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Unit)) return false;
    return symbol().equals(((Unit) obj).symbol());
  }
}
