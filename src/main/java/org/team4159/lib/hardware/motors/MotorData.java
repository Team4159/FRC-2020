package org.team4159.lib.hardware.motors;

public class MotorData {
  public String name;
  public double free_speed,
                free_current,
                max_power,
                stall_torque,
                stall_current;
  public MotorData(String name, double free_speed, double free_current,
                   double max_power, double stall_torque, double stall_current) {
    this.name = name;
    this.free_speed = free_speed;
    this.free_current = free_current;
    this.max_power = max_power;
    this.stall_torque = stall_torque;
    this.stall_current = stall_current;
  }
}
