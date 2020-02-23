package org.team4159.lib.simulation.mocks;

// model of a relative encoder and the transmission's real position
// uses doubles over ints to allow for simulation over small timesteps but it's kind of iffy

public class RelativeEncoderMock {
  private double real_position = 0;
  private double encoder_position = 0;

  public void move(double ticks) {
    real_position += ticks;
    encoder_position += ticks;
  }

  public void setRealPosition(double position) {
    this.real_position = position;
  }

  public void setEncoderPosition(double position) {
    this.encoder_position = position;
  }

  public double getEncoderPosition() {
    return encoder_position;
  }

  public double getRealPosition() {
    return real_position;
  }
}
