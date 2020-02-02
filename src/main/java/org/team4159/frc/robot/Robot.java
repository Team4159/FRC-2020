package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer robot_container;
  private Command autonomous_command;

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
    autonomous_command = robot_container.getAutonomousCommand();
    if (autonomous_command != null) {
      autonomous_command.schedule();
    }
  }

  @Override
  public void teleopInit() {
    if (autonomous_command != null) {
      autonomous_command.cancel();
    }
  }
}
