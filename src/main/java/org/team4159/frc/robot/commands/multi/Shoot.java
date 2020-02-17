package org.team4159.frc.robot.commands.multi;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Neck;
import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.frc.robot.subsystems.Turret;

public class Shoot extends CommandBase {
  private Turret turret;
  private Shooter shooter;
  private Neck neck;
  private double target_speed;

  private boolean shooting;

  public Shoot(double target_speed, Shooter shooter, Neck neck, Turret turret) {
    this.shooter = shooter;
    this.neck = neck;
    this.turret = turret;

    this.target_speed = target_speed;

    addRequirements(shooter, neck);
  }

  @Override
  public void initialize() {
    shooter.setTargetSpeed(target_speed);
  }

  @Override
  public void execute() {
    if (turret.isPointingAtTarget() && shooter.isAtTargetSpeed()) {
      neck.neck();
    } else {
      neck.stop();
    }
  }
}
