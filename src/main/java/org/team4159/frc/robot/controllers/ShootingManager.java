package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.Timer;

import org.team4159.frc.robot.subsystems.Neck;
import org.team4159.lib.control.ControlLoop;

public class ShootingManager implements ControlLoop {
  private enum State {
    IDLE,
    WAITING,
    SHOOTING
  }

  private static final double kShootingIntervalSeconds = 1.0;

  private Neck neck;
  private TurretController turret_controller;
  private ShooterController shooter_controller;

  private State state = State.IDLE;
  private Timer timer = new Timer();

  public ShootingManager(Neck neck, TurretController turret_controller, ShooterController shooter_controller) {
    this.neck = neck;
    this.turret_controller = turret_controller;
    this.shooter_controller = shooter_controller;

    timer.start();
  }

  @Override
  public void update() {
    switch(state) {
      case IDLE:
        break;
      case WAITING:
        if (isShooterReadyToShoot()) {
          state = State.SHOOTING;
        } else {
          neck.stop();
        }
        break;
      case SHOOTING:
        if (isShooterReadyToShoot()) {
          state = State.WAITING;
        } else {
          neck.neck();
        }
        break;
    }
  }

  private boolean isShooterReadyToShoot() {
    //return turret_controller.isTurretPointingAtTarget() && shooter_controller.isAtTargetSpeed();
    if (turret_controller.isTurretPointingAtTarget() && shooter_controller.isAtTargetSpeed()) {
      if (timer.hasPeriodPassed(kShootingIntervalSeconds)) {
        timer.reset();
        return true;
      }
    }
    return false;
  }

  public void beginShooting() {
    turret_controller.setState(TurretController.State.SEEKING_TARGET);
    shooter_controller.setState(ShooterController.State.CLOSED_LOOP);

    state = State.WAITING;
  }

  public void stopShooting() {
    turret_controller.setState(TurretController.State.OPEN_LOOP);
    shooter_controller.setState(ShooterController.State.IDLE);

    state = State.IDLE;
  }
}
