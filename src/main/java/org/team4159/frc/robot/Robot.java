package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.team4159.frc.robot.commands.arm.ZeroArm;
import org.team4159.frc.robot.commands.turret.LimelightSeek;
import org.team4159.frc.robot.commands.turret.ZeroTurret;

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

    System.out.println(Constants.TURRET_CONSTANTS.FORWARD_POSITION);
  }

  @Override
  public void autonomousInit() {
    autonomous_command = robot_container.getAutonomousCommand();
    if (autonomous_command != null) autonomous_command.schedule();
  }

  @Override
  public void teleopInit() {
    if (autonomous_command != null) autonomous_command.cancel();


    //new ZeroArm(robot_container.arm).schedule(false);
    //new ZeroTurret(robot_container.turret).andThen(new LimelightSeek(robot_container.turret)).schedule(false);
  }
}
