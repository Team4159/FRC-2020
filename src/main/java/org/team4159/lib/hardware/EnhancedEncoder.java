package org.team4159.lib.hardware;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;

public class EnhancedEncoder {
  private Encoder encoder;

  private Timer timer;
  private int last_position;

  public EnhancedEncoder(Encoder encoder) {
    this.encoder = encoder;
    timer = new Timer();
    timer.start();
    last_position = encoder.get();
  }

  public int getPosition() {
    int position = encoder.get();
    last_position = position;
    return position;
  }

  public double getVelocity() {
    int position = encoder.get();
    int last_position_temp = last_position;
    last_position = position;
    double delta_time = timer.get();
    timer.reset();
    return (position - last_position_temp) / delta_time;
  }

  public Encoder getEncoder() {
    return encoder;
  }
}
