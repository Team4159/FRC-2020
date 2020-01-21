package org.team4159.lib.units;

public class Frac extends Unit {
  private Unit n, d;

  Frac(Unit n, Unit d) {
    this.n = n;
    this.d = d;
  }

  public Value of(double amount) {
    return of(amount, new Frac(n, d));
  }

  @Override
  public String getName() {
    return n.getName() + "/" + d.getName();
  }
}
