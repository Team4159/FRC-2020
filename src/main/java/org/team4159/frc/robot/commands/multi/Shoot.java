package org.team4159.frc.robot.commands.multi;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Neck;
import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.frc.robot.subsystems.Turret;

public class Shoot extends CommandBase {
  private Turret turret;
  private Shooter shooter;
  private Neck neck;

  public Shoot(Turret turret, Shooter shooter, Neck neck) {
    this.turret = turret;
    this.shooter = shooter;
    this.neck = neck;

    addRequirements(turret, shooter, neck);
  }

  @Override
  public void initialize() {

  }
}
