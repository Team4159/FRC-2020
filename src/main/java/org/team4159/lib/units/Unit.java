package org.team4159.lib.units;

public class Unit {
  public Value of(double amount) {
    return of(amount, new Unit());
  }

  protected Value of(double amount, Unit unit) {
    return new Value(amount, unit);
  }

  public Frac divide(Unit unit) {
    return new Frac(this, unit);
  }

  public String getName() {
    return "ul";
  }

  @Override
  public String toString() {
    return getName();
  }
}
