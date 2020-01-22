package org.team4159.units;

import org.junit.Assert;
import org.junit.Test;

import static org.team4159.lib.math.units.Units.*;

public class DerivedUnitTest {
  @Test
  public void TestNewton() {
    Assert.assertEquals(NEWTON.symbol(), "kgm/s^2");
  }

  @Test
  public void TestVolt() {
    Assert.assertEquals(VOLT.symbol(), "m^2kg/As^3");
  }
}
