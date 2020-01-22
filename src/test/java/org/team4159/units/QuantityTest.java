package org.team4159.units;

import org.junit.Assert;
import org.junit.Test;

import org.team4159.lib.math.units.Quantity;
import org.team4159.lib.math.units.derivatives.Velocity;

import static org.team4159.lib.math.units.Units.*;

public class QuantityTest {
  @Test
  public void TestUnitsCancel() {
    Quantity velocity = new Quantity(5.0, Velocity.of(METER));
    Quantity time = new Quantity(1.0, SECOND);

    Quantity distance = velocity.mult(time);

    Assert.assertEquals("5.0 m", distance.toString());
  }
}
