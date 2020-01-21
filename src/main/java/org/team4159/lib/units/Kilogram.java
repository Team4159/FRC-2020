package org.team4159.lib.units;

public class Kilogram extends Unit {
  public Value of(double amount) {
    return of(amount, new Kilogram());
  }

  @Override
  public String getName() {
    return "kg";
  }
}
