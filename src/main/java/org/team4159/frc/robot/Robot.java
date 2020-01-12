package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer robot_container;

  @Override
  public void robotInit() {
    robot_container = new RobotContainer();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    robot_container.writeLogOutputs();
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
