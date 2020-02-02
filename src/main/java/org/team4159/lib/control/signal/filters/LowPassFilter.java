package org.team4159.lib.control.signal.filters;

  import edu.wpi.first.wpilibj.TimedRobot;

public class LowPassFilter {
  private double smoothed, smoothing, period;

  public LowPassFilter(double initial_value, double smoothing, double period) {
    this.smoothed = initial_value;
    this.smoothing = smoothing;
    this.period = period;
  }

  public LowPassFilter(double initial_value, double smoothing) {
    this(initial_value, smoothing, TimedRobot.kDefaultPeriod);
  }

  public double calculate(double value) {
    smoothed += (value - smoothed) / smoothing * period;
    return smoothed;
  }
}
