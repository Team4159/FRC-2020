package org.team4159.lib.simulation;

import edu.wpi.first.wpilibj.TimedRobot;

public class SimulationConfiguration {
  public static double SIMULATION_TIMESTEP = 0.001; // seconds
  public static double CONTROL_LOOP_TIMESTEP = TimedRobot.kDefaultPeriod; // seconds
  public static double LOSS_COEFFICIENT = 0.8;
}
