package org.team4159.lib.math.units.derivatives;

import org.team4159.lib.math.units.Unit;
import static org.team4159.lib.math.units.Units.*;

public class Acceleration {
  public static Unit of(Unit unit) {
    return unit.div(SECOND).div(SECOND);
  }
}
