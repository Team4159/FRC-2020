package org.team4159.lib.math.physics;

// model of a DC motor

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

  // derived constants

  public double resistance() {
    return test_voltage / stall_current; // V = IR -> R = V/I
  }

  public double voltage_constant() {
    return free_speed / (test_voltage - free_current * resistance()); // voltage dropped at free speed = free_current * resistance
  }

  public double back_emf_constant() {
    return 1 / voltage_constant();
  }

  public double torque_constant() {
    return stall_torque / stall_current;
  }

  public double get_current_for_speed(final double rpm, final double voltage) {
    return (voltage - rpm * back_emf_constant()) / resistance();
  }

  public double get_torque_for_speed(final double rpm, final double voltage) {
    return torque_constant() * get_current_for_speed(rpm, voltage);
  }
}
