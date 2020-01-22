package org.team4159.units;

import org.junit.Assert;
import org.junit.Test;

import static org.team4159.lib.math.units.Units.*;

public class DerivedUnitTest {
  @Test
  public void TestNewton() {
    Assert.assertEquals("kgm/s^2", NEWTON.symbol());
  }

  @Test
  public void TestVolt() {
    Assert.assertEquals("m^2kg/As^3", VOLT.symbol());
  }
}
