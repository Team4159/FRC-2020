package org.team4159.lib.control.signal.filters;

import java.util.function.DoubleSupplier;

public class LowPassFilterSource {
  private LowPassFilter filter;
  private DoubleSupplier source;

  public LowPassFilterSource(DoubleSupplier source, double smoothing) {
    this.source = source;
    this.filter = new LowPassFilter(source.getAsDouble(), smoothing);
  }

  public double get() {
    return filter.calculate(source.getAsDouble());
  }
}
