package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Turret;

import static org.team4159.frc.robot.Constants.*;

public class ZeroTurret extends CommandBase {
  private Turret turret;
  private boolean reversed;

  public ZeroTurret(Turret turret, boolean reversed) {
    this.turret = turret;
    this.reversed = reversed;

    addRequirements(turret);
  }

  @Override
  public void execute() {
    turret.setRawSpeed((reversed ? -1 : 1) * TURRET_CONSTANTS.ZEROING_SPEED);
  }

  @Override
  public boolean isFinished() {
    // TODO: check direction
    return turret.isForwardLimitSwitchClosed();
  }

  @Override
  public void end(boolean interrupted) {
    turret.setEncoderPosition(TURRET_CONSTANTS.TICK_RANGE);
    turret.stop();
  }
}
