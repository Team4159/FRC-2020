package org.team4159.frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import org.team4159.frc.robot.subsystems.Drivetrain;

public class Robot extends TimedRobot {
  private RobotContainer robot_container;
  private Drivetrain drivetrain;

  /** for characterization routine **/
  private NetworkTableEntry auto_speed_entry;
  private NetworkTableEntry telemetry_entry;
  private NetworkTableEntry rotate_entry;

  private double prior_autospeed = 0;

  @Override
  public void robotInit() {
    NetworkTableInstance.getDefault().setUpdateRate(0.010);

    auto_speed_entry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
    telemetry_entry = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
    rotate_entry = NetworkTableInstance.getDefault().getEntry("/robot/rotate");

    prior_autospeed = 0;

    robot_container = new RobotContainer();
    drivetrain = robot_container.drivetrain;
  }

  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();
  }

  /** characterization routine **/
  @Override
  public void autonomousPeriodic() {
    // Retrieve values to send back before telling the motors to do something
    double timestamp = Timer.getFPGATimestamp();

    double battery = RobotController.getBatteryVoltage();

    double left_pos = drivetrain.getLeftDistance();
    double left_rate = drivetrain.getLeftRate();
    double right_pos = drivetrain.getRightDistance();
    double right_rate = drivetrain.getRightRate();
    double left_volts = drivetrain.getLeftVoltage();
    double right_volts = drivetrain.getRightVoltage();

    double drivetrain_angle = Math.toRadians(drivetrain.getHeading());

    // Retrieve the commanded speed from NetworkTables
    double autospeed = auto_speed_entry.getDouble(0);

    // command motors to do things
    drivetrain.rawDrive((rotate_entry.getBoolean(false) ? -1 : 1) * autospeed, autospeed);

    // send telemetry data array back to NT
    telemetry_entry.setNumberArray(new Number[] {
            timestamp,
            battery,
            autospeed,
            left_volts,
            right_volts,
            left_pos,
            right_pos,
            left_rate,
            right_rate,
            drivetrain_angle
    });
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
