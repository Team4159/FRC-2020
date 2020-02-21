package org.team4159.frc.robot.subsystems;

import org.team4159.lib.simulation.mocks.SubsystemMock;

public class ShooterTest {
  public class MockShooter extends Shooter implements SubsystemMock {
    @Override
    public void simulate(double dt) {

    }

    @Override
    public void setRawSpeed(double speed) {
      super.setRawSpeed(speed);
    }

    @Override
    public void setRawVoltage(double voltage) {
      super.setRawVoltage(voltage);
    }

    @Override
    public double getSpeed() {
      return super.getSpeed();
    }
  }
}
