package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.hardware.Limelight;


public class limelightRPM extends CommandBase {
  private Limelight limelight;
  private Shooter shooter;
  private double distance;
  private double voltage_constant= 0;
  private double n = 1;

  public limelightRPM(Shooter shooter) {
    this.shooter = shooter;
    limelight = shooter.getLimelight();
    addRequirements(shooter);
  }

  private double calculateVoltage(double dist) {
    return voltage_constant * Math.pow(dist, n);
  }

  @Override
  public void initialize() {
    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
    distance = shooter.getDistanceToVisionTarget();
  }

  @Override
  public void execute() {
    shooter.setRawSpeed(calculateVoltage(distance));
  }

  @Override
  public void end(boolean interrupted) {
    limelight.setLEDMode(Limelight.LEDMode.ForceOff);
    shooter.stop();
  }
}
