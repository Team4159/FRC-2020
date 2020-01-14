package org.team4159.frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.traj.Trajectories;

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
