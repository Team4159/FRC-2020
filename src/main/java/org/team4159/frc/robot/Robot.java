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
  }

  @Override
  public void autonomousInit() {
    robot_container.zeroSubsystems();
  }

  @Override
  public void teleopInit() {
    robot_container.zeroSubsystems();
  }

  @Override
  public void teleopPeriodic() {
    robot_container.updateSubsystemInputs();
    // robot_container.updateControllerInputs();
  }
}
