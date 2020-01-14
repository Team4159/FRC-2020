package org.team4159.frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import org.team4159.frc.robot.subsystems.Drivetrain;

public class CharacterizationRoutine extends CommandBase {
  private Drivetrain drivetrain;

  private NetworkTableEntry auto_speed_entry;
  private NetworkTableEntry telemetry_entry;
  private NetworkTableEntry rotate_entry;

  public CharacterizationRoutine(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    // set update rate to 10ms, needed for characterization routine
    NetworkTableInstance.getDefault().setUpdateRate(0.010);

    auto_speed_entry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
    telemetry_entry = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");
    rotate_entry = NetworkTableInstance.getDefault().getEntry("/robot/rotate");
  }

  @Override
  public void execute() {
    // Retrieve values to send back before telling the motors to do something
    double timestamp = Timer.getFPGATimestamp();
    double battery = RobotController.getBatteryVoltage();
    double drivetrain_angle = Math.toRadians(drivetrain.getDirection());

    double left_pos = drivetrain.getLeftDistance();
    double left_rate = drivetrain.getLeftVelocity();
    double right_pos = drivetrain.getRightDistance();
    double right_rate = drivetrain.getRightVelocity();
    double left_volts = drivetrain.getLeftVoltage();
    double right_volts = drivetrain.getRightVoltage();

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

    // Provide visuals to humans
    SmartDashboard.putNumber("l_encoder_pos", left_pos);
    SmartDashboard.putNumber("l_encoder_rate", left_rate);
    SmartDashboard.putNumber("r_encoder_pos", right_pos);
    SmartDashboard.putNumber("r_encoder_rate", right_rate);
  }
}
