package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.TimedRobot;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.util.Units;

import org.team4159.lib.math.Conversions;
import org.team4159.lib.math.Epsilon;
import org.team4159.lib.simulation.RelativeEncoderMock;
import org.team4159.lib.simulation.MotorModels;
import org.team4159.lib.math.physics.DCMotorModel;

import org.team4159.frc.robot.controllers.ArmController;

import static org.team4159.lib.simulation.SimulationConfiguration.*;
import static org.team4159.frc.robot.Constants.*;

public class ArmTest {
  public class MockArm extends Arm {
    private double inertia = Conversions.poundsToKilograms(3) * Math.pow(Units.inchesToMeters(8), 2); // kg m^2
    private DCMotorModel motor;

    private double applied_voltage = 0;
    private double angular_velocity = 0; // rad / s
    private RelativeEncoderMock encoder;

    MockArm() {
      encoder = new RelativeEncoderMock();
      motor = MotorModels.NEO_550;
    }

    void simulate(double dt) {
       double torque = motor.getTorqueForVoltage(angular_velocity, applied_voltage);
       double angular_acceleration = torque / inertia;
       angular_velocity += angular_acceleration * LOSS_COEFFICIENT * dt;
       encoder.move(ARM_CONSTANTS.GEARING.COUNTS_PER_RADIAN * angular_velocity * dt);
    }

    void setRealStartingPosition(int position) {
      encoder.setRealPosition(position);
    }

    double getRealPosition() {
      return encoder.getRealPosition();
    }

    double getAppliedVoltage() {
      return applied_voltage;
    }

    double getAngularVelocity() {
      return angular_velocity;
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
    public void zeroEncoder() {
      encoder.setEncoderPosition(0);
    }

    @Override
    public int getPosition() {
      return (int) encoder.getEncoderPosition();
    }

    @Override
    public boolean isLimitSwitchClosed() {
      return encoder.getRealPosition() <= 0;
    }
  }

  private MockArm arm;
  private ArmController arm_controller;

  @Before
  public void reset() {
    arm = new MockArm();
    arm_controller = new ArmController(arm);
  }

  private void simulateTime(double time) {
    while (time > 0) {
      if (Epsilon.epsilonEquals(time % TimedRobot.kDefaultPeriod, 0, 1E-3)) {
        // System.out.println(arm.getRealPosition() + ", " + arm.getAppliedVoltage() + ", " + arm.getPosition());
        arm_controller.update();
      }
      arm.simulate(SIMULATION_TIMESTEP);
      time -= SIMULATION_TIMESTEP;
    }
  }

  @Test
  public void Zeroes() {
    arm.setRealStartingPosition(100);
    simulateTime(5);
    Assert.assertEquals(0, arm.getRealPosition(), ARM_CONSTANTS.ACCEPTABLE_ERROR_IN_COUNTS);
  }
}
