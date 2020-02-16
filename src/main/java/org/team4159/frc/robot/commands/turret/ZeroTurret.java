package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Turret;

import static org.team4159.frc.robot.Constants.*;

public class ZeroTurret extends CommandBase {
  private Turret turret;

  public ZeroTurret(Turret turret) {
    this.turret = turret;

    addRequirements(turret);
  }

  @Override
  public void execute() {
    turret.setRawSpeed(TURRET_CONSTANTS.ZEROING_SPEED);

    turret.setZeroed(false);
  }

  @Override
  public boolean isFinished() {
    // TODO: check direction
    return turret.isReverseLimitSwitchClosed();
  }

  @Override
  public void end(boolean interrupted) {
    turret.setZeroed(true);

    turret.stop();
  }
}
