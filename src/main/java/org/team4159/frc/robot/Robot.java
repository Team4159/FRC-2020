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
  private NetworkTableEntry auto_speed_entry =
          NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
  private NetworkTableEntry telemetry_entry =
          NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
  private NetworkTableEntry rotate_entry =
          NetworkTableInstance.getDefault().getEntry("/robot/rotate");

  private double prior_autospeed = 0;

  private Number[] numberArray = new Number[10];

  @Override
  public void robotInit() {
    NetworkTableInstance.getDefault().setUpdateRate(0.010);

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
    double now = Timer.getFPGATimestamp();

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
    prior_autospeed = autospeed;

    // command motors to do things
    drivetrain.rawDrive((rotate_entry.getBoolean(false) ? -1 : 1) * autospeed, autospeed);

    // send telemetry data array back to NT
    numberArray[0] = now;
    numberArray[1] = battery;
    numberArray[2] = autospeed;
    numberArray[3] = left_volts;
    numberArray[4] = right_volts;
    numberArray[5] = left_pos;
    numberArray[6] = right_pos;
    numberArray[7] = left_rate;
    numberArray[8] = right_rate;
    numberArray[9] = drivetrain_angle;

    telemetry_entry.setNumberArray(numberArray);
  }

  @Override
  public void testInit() {
    CommandScheduler.getInstance().cancelAll();
  }
}
