package org.team4159.frc.robot.controllers;

import org.team4159.frc.robot.subsystems.INeck;
import org.team4159.lib.control.IControlLoop;

public class ShootingManager implements IControlLoop {
  private enum State {
    IDLE,
    WAITING,
    SHOOTING
  }

  private INeck neck;
  private TurretController turret_controller;
  private ShooterController shooter_controller;

  private State state = State.IDLE;

  public ShootingManager(INeck neck, TurretController turret_controller, ShooterController shooter_controller) {
    this.neck = neck;
    this.turret_controller = turret_controller;
    this.shooter_controller = shooter_controller;
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
    return turret_controller.isTurretPointingAtTarget() && shooter_controller.isAtTargetSpeed();
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
