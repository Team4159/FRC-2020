package org.team4159.frc.robot;

import java.io.File;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.team4159.frc.robot.util.LogUtil;

public class Robot extends TimedRobot {
  private double last_log_time = Timer.getFPGATimestamp();

  private RobotContainer robot_container;
  private BadLog log;

  @Override
  public void robotInit() {
    String bag_name = LogUtil.generateHexString();
    File bag_folder = LogUtil.findUSBDrive().orElse(new File("/home/lvuser/logs"));

    log = BadLog.init(bag_folder.toPath().resolve(bag_name + ".bag").toString());
    robot_container = new RobotContainer();
    log.finishInitialization();
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
    double current_time = Timer.getFPGATimestamp();
    double time_delta = last_log_time - current_time;
    if (isEnabled() || isDisabled() && time_delta >= 0.25) {
      last_log_time = current_time;
      // TODO: check if we have to do this in a separate thread
      log.log();
    }
  }

  @Override
  public void teleopInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
