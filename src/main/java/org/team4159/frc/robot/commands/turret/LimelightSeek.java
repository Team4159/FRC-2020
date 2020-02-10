package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class LimelightSeek extends CommandBase {
  private Turret turret;
  private Limelight limelight;

  private PIDController pid_controller = new PIDController(
    TURRET_CONSTANTS.LIMELIGHT_TURN_kP,
    0,
    TURRET_CONSTANTS.LIMELIGHT_TURN_kD
  );

  public LimelightSeek(Turret turret) {
    this.turret = turret;
    this.limelight = turret.getLimelight();

    addRequirements(turret);
  }

  @Override
  public void initialize() {
    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
    pid_controller.reset();
  }

  @Override
  public void execute() {
    turret.setRawSpeed(pid_controller.calculate(limelight.getTargetHorizontalOffset()));
  }

  @Override
  public void end(boolean interrupted) {
    limelight.setLEDMode(Limelight.LEDMode.ForceOff);
    turret.stop();
  }
}
