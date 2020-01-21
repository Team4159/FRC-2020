package org.team4159.lib.units;

public class Value {
  private double amount;
  private Unit unit;

  Value(double amount, Unit unit) {
    this.amount = amount;
    this.unit = unit;
  }

  @Override
  public String toString() {
    return amount + " " + unit.getName();
  }
}
