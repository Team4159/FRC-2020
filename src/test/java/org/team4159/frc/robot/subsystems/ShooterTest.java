package org.team4159.frc.robot.subsystems;

import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.simulation.mocks.ISubsystemMock;

public class ShooterTest {
  public class MockShooter implements IShooter, ISubsystemMock {
    @Override
    public void simulate(double dt) {

    }

    @Override
    public void setRawSpeed(double speed) {

    }

    @Override
    public void setRawVoltage(double voltage) {

    }

    @Override
    public double getSpeed() {
      return 0;
    }

    @Override
    public Limelight getLimelight() {
      return null;
    }
  }
}
