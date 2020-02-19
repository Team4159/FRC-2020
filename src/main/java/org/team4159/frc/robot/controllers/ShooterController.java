package org.team4159.frc.robot.controllers;

import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.hardware.Limelight;

public class ShooterController implements ControlLoop {
  private enum State {
    IDLE,
    CLOSED_LOOP
  }

  private Shooter shooter;
  private Limelight limelight;

  private double target_rpm = 0;

  private State state = State.IDLE;

  public ShooterController(Shooter shooter) {
    this.shooter = shooter;
    this.limelight = shooter.getLimelight();
  }

  @Override
  public void update() {
    switch(state) {
      case IDLE:
        shooter.setTargetSpeed(0);
        break;
      case CLOSED_LOOP:
        if (limelight.isTargetVisible()) {
          // In theory, the velocity of the ball should scale linearly with RPM, which in turn scales linearly with horizontal distance (I think)
          // I'll update this math later
          double distance = shooter.getDistanceToVisionTarget();
          double k = 1;

          // Y Intercept measured empirically
          double b = 0;

          target_rpm = distance * k + b;
        }

        shooter.setTargetSpeed(target_rpm);
        break;
    }
  }
}
