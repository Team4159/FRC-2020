package org.team4159.lib.math.units;

public class Quantity {
  private Unit unit;
  private double amount;

  public Quantity(double amount, Unit unit) {
    this.amount = amount;
    this.unit = unit;
  }

  public Quantity mult(Quantity other) {
    return new Quantity(amount * other.amount, unit.mult(other.unit));
  }

  public Quantity div(Quantity other) {
    return mult(new Quantity(1.0 / other.amount, other.unit.inv()));
  }

  @Override
  public String toString() {
    return amount + " " + unit.symbol();
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Quantity)) return false;
    Quantity other = (Quantity) obj;
    return this.amount == other.amount && this.unit.equals(other.unit);
  }
}
