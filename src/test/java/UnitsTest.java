import org.junit.Assert;
import org.junit.Test;

import org.team4159.lib.units.derivatives.Acceleration;
import org.team4159.lib.units.derivatives.Velocity;
import org.team4159.lib.units.Units;

public class UnitsTest {
  @Test
  public void TestVelocity() {
    Assert.assertEquals(Velocity.of(Units.METER).toString(), "m/s");
  }

  @Test
  public void TestAcceleration() {
    Assert.assertEquals(Acceleration.of(Units.METER).toString(), "m/s/s");
  }

  @Test
  public void TestValue() {
    Assert.assertEquals(Acceleration.of(Units.METER).of(1).toString(), "1.0 m/s/s");
  }
}
