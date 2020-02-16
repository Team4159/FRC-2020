package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class LimelightSeek extends CommandBase {
  private Turret turret;
  private Limelight limelight;

  private boolean seeking = false;
  private int seeking_range = TURRET_CONSTANTS.STARTING_SEEKING_RANGE;
  private int seeking_direction;
  private int starting_position;

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
    if (limelight.isTargetVisible()) {
      seeking = false;
      turret.setRawSpeed(pid_controller.calculate(limelight.getTargetHorizontalOffset()));
    } else {
      if (seeking) {
        turret.setRawSpeed(TURRET_CONSTANTS.SEEKING_SPEED * seeking_direction);
        if (Math.abs(turret.getPosition() - starting_position) > seeking_range) {
          seeking_direction = -seeking_direction;
          seeking_range += TURRET_CONSTANTS.SEEKING_RANGE_INCREMENT;
        }
      } else {
        seeking = true;
        starting_position = turret.getPosition();
        seeking_direction = -1;
      }
    }
  }

  @Override
  public void end(boolean interrupted) {
    limelight.setLEDMode(Limelight.LEDMode.ForceOff);
    turret.stop();
  }
}
