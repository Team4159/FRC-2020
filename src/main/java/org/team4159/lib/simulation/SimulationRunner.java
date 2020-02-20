package org.team4159.lib.simulation;

import org.team4159.lib.control.IControlLoop;
import org.team4159.lib.math.MathUtil;
import org.team4159.lib.simulation.mocks.ISubsystemMock;

import static org.team4159.lib.simulation.SimulationConfiguration.*;

public class SimulationRunner {
  // runs a simulated subsystem at a short timestep alongside a control loop at the usual timestep
  public static void simulate(ISubsystemMock subsystem_mock, IControlLoop control_loop, double time) {
    while (time > 0) {
      if (MathUtil.epsilonEquals(time % CONTROL_LOOP_TIMESTEP, 0, 1E-3)) {
        control_loop.update();
      }
      subsystem_mock.simulate(SIMULATION_TIMESTEP);
      time -= SIMULATION_TIMESTEP;
    }
  }
}
