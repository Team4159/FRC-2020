package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
  private RobotContainer robot_container;
  private CommandBase m_autonomous_command;

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
    //robot_container.zeroSubsystems(); This should probably be part of whatever auto is running

  m_autonomous_command = robot_container.getAutoCommand();
  if (m_autonomous_command != null) {
    m_autonomous_command.schedule();
  }
  }

  @Override
  public void teleopInit() {
    System.out.println("teleop init");
    robot_container.zeroSubsystems();
  }

  @Override
  public void teleopPeriodic() {
    robot_container.updateControllerInputs();
    robot_container.updateSubsystemInputs();
  }
}
