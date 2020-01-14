package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.team4159.frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
  private RobotContainer robot_container;
  private Drivetrain drivetrain;

  private Command autonomous_command;

  @Override
  public void robotInit() {
    robot_container = new RobotContainer();

    drivetrain = robot_container.drivetrain;
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    autonomous_command = robot_container.getAutonomousCommand();
    autonomous_command.schedule();
  }

  @Override
  public void teleopInit() {
    autonomous_command.cancel();
  }
}
