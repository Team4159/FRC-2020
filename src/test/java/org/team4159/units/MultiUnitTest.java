package org.team4159.units;

import org.junit.Assert;
import org.junit.Test;

import org.team4159.lib.math.units.derivatives.Velocity;
import org.team4159.lib.math.units.derivatives.Acceleration;

import static org.team4159.lib.math.units.Units.*;

public class MultiUnitTest {
  @Test
  public void TestVelocityMultiUnit() {
    Assert.assertEquals("m/s", Velocity.of(METER).symbol());
  }

  @Test
  public void TestAccelerationMultiUnit() {
    Assert.assertEquals("m/s^2", Acceleration.of(METER).symbol());
  }

  @Test
  public void TestUnitsCancel() {
    Assert.assertEquals("", SECOND.div(SECOND).symbol());
  }

  @Test
  public void TestIsCommutative() {
    Assert.assertEquals(METER.mult(KILOGRAM).symbol(), KILOGRAM.mult(METER).symbol());
  }

  @Test
  public void TestDoesntMutate() {
    METER.mult(SECOND);
    Assert.assertEquals("m", METER.symbol());
  }

  @Test
  public void TestEquality() {
    Assert.assertEquals(Velocity.of(METER), Velocity.of(METER));
  }
}
