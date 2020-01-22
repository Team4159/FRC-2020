package org.team4159.lib.math.units;

import java.util.Map;

public class Unit {
  public String symbol() {
    return "ul";
  }

  public Unit mult(Unit other) {
    if (other instanceof MultiUnit) {
      return other.mult(this);
    } else {
      return new MultiUnit(Map.of(this, 1, other, 1));
    }
  }

  public Unit div(Unit other) {
    return mult(other.inv());
  }

  public Unit inv() {
    return new MultiUnit(Map.of(this, -1));
  }

  @Override
  public String toString() {
    return symbol();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Unit)) return false;
    return symbol().equals(((Unit) obj).symbol());
  }
}
