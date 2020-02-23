package org.team4159.frc.robot.subsystems;

import org.team4159.lib.math.MathUtil;
import org.team4159.lib.math.physics.DCMotorModel;
import org.team4159.lib.simulation.MotorModels;
import org.team4159.lib.simulation.mocks.SubsystemMock;

import static org.team4159.lib.simulation.SimulationConfiguration.*;
import static org.team4159.frc.robot.Constants.*;

public class ShooterTest {
  public class MockShooter extends Shooter implements SubsystemMock {
    private double inertia = 0;
    private DCMotorModel[] motors;

    private double applied_voltage = 0;
    private double angular_velocity = 0; // rad / s

    MockShooter() {
      motors = new DCMotorModel[] {
        MotorModels._775pro,
        MotorModels._775pro
      };
    }

    @Override
    public void simulate(double dt) {
      double torque = 0;
      for (DCMotorModel motor : motors) {
        torque += motor.getTorqueForVoltage(angular_velocity, applied_voltage);
      }
      double angular_acceleration = torque / inertia;
      angular_velocity += angular_acceleration * LOSS_COEFFICIENT * dt;
    }

    @Override
    public void setRawSpeed(double speed) {
      setRawVoltage(speed * 12);
    }

    @Override
    public void setRawVoltage(double voltage) {
      applied_voltage = voltage;
    }

    @Override
    public double getSpeed() {
      return angular_velocity / MathUtil.kTau * ENCODERS.THROUGH_BORE_ENCODER_CPR;
    }
  }
}
