package org.team4159.lib.math.physics;

// model of a DC motor
/*
 * Adapted from: Team 254
 * https://github.com/Team254/FRC-2018-Public/blob/master/src/main/java/com/team254/lib/physics/DCMotorTransmission.java
 */

public class DCMotorModel {
  public final String name;
  public final double
    free_speed, // rpm
    free_current, // amps
    stall_torque, // newton-meters
    stall_current, // amps
    test_voltage; // volts

  public DCMotorModel(String name,
                      double free_speed, double free_current,
                      double stall_torque, double stall_current,
                      double test_voltage) {
    this.name = name;
    this.free_speed = free_speed;
    this.free_current = free_current;
    this.stall_torque = stall_torque;
    this.stall_current = stall_current;
    this.test_voltage = test_voltage;
  }

  // https://en.wikipedia.org/wiki/Motor_constants

  public double resistance() {
    return test_voltage / stall_current; // V = IR -> R = V/I
  }

  // "velocity constant"
  public double volts_per_speed() {
    return free_speed / (test_voltage - free_current * resistance()); // voltage dropped at free speed = free_current * resistance
  }

  // "back-EMF constant"
  public double speed_per_volt() {
    return 1 / volts_per_speed();
  }

  // "torque constant"
  public double torque_per_amp() {
    return stall_torque / stall_current;
  }

  public double torque_per_volt() {
    return torque_per_amp() / resistance();
  }

  public double getTorqueForVoltage(final double output_speed, final double voltage) {
    return torque_per_volt() * (voltage - output_speed * volts_per_speed());
  }
}
