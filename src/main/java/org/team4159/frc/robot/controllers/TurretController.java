package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class TurretController implements ControlLoop {
  private enum State {
    ZEROING,
    SEEKING_TARGET,
    FOUND_TARGET,
    RECOVERING,
    IDLE
  }
  private State last_state;
  private State state = State.IDLE;

  private Turret turret;
  private Limelight limelight;

  private int seeking_direction, seeking_range, seeking_starting_position;

  private PIDController pid_controller = new PIDController(
    TURRET_CONSTANTS.LIMELIGHT_TURN_kP,
    TURRET_CONSTANTS.LIMELIGHT_TURN_kI,
    TURRET_CONSTANTS.LIMELIGHT_TURN_kD
  );

  public TurretController(Turret turret, Limelight limelight) {
    this.turret = turret;
    this.limelight = limelight;

    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
    pid_controller.reset();
  }

  @Override
  public void update() {
    if (turret.isForwardLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.FORWARD_POSITION);
    } else if (turret.isReverseLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.REVERSE_POSITION);
    }

    if (isTurretOutOfSafeRange() && state != State.ZEROING) {
      setState(State.RECOVERING);
    }

    switch (state) {
      case ZEROING:
        if (turret.isForwardLimitSwitchClosed()) {
          setState(State.IDLE);
        } else {
          turret.setRawSpeed(TURRET_CONSTANTS.ZEROING_SPEED);
        }
        break;
      case SEEKING_TARGET:
        if (limelight.isTargetVisible()){
          setState(State.FOUND_TARGET);
        } else {
          turret.setRawSpeed(seeking_direction * TURRET_CONSTANTS.SEEKING_SPEED);

          if (Math.abs(turret.getPosition() - seeking_starting_position) > seeking_range) {
            seeking_direction *= -1.0;

            int new_seeking_range = seeking_range + TURRET_CONSTANTS.SEEKING_RANGE_INCREMENT;

            if (!(turret.getPosition() + new_seeking_range > TURRET_CONSTANTS.SAFE_FORWARD_POSITION
                && turret.getPosition() - new_seeking_range < TURRET_CONSTANTS.SAFE_REVERSE_POSITION)) {
              seeking_range = new_seeking_range;
            }
          }
        }
        break;
      case FOUND_TARGET:
        if (!limelight.isTargetVisible()) {
          setState(State.SEEKING_TARGET);
        } else {
          turret.setRawSpeed(pid_controller.calculate(limelight.getTargetHorizontalOffset()));
        }
        break;
      case RECOVERING:
        if (turret.getPosition() > TURRET_CONSTANTS.SAFE_FORWARD_POSITION) {
          turret.setRawSpeed(TURRET_CONSTANTS.ZEROING_SPEED);
        } else if (turret.getPosition() < TURRET_CONSTANTS.SAFE_REVERSE_POSITION) {
          turret.setRawSpeed(-1.0 * TURRET_CONSTANTS.ZEROING_SPEED);
        } else {
          setState(last_state);
        }
        break;
      case IDLE:
        break;
    }
  }

  public boolean isTurretPointingAtTarget() {
    return Math.abs(limelight.getTargetHorizontalOffset()) < 1;
  }

  public boolean isTurretOutOfSafeRange() {
    return Math.abs(turret.getPosition()) < TURRET_CONSTANTS.SAFE_FORWARD_POSITION;
  }

  public void startZeroing() {
    state = State.ZEROING;
  }

  public void startSeeking() {
    seeking_direction = -1;
    seeking_range = TURRET_CONSTANTS.STARTING_SEEKING_RANGE;
    seeking_starting_position = turret.getPosition();
    state = State.SEEKING_TARGET;
  }

  public void startRecovering() {
    last_state = state;
    state = State.RECOVERING;
  }

  public void idle() {
    setState(State.IDLE);
  }

  private void setState(State state) {
    if (state == State.SEEKING_TARGET) {
      startSeeking();
    } else if (state == State.RECOVERING) {
      startRecovering();
    } else {
      this.state = state;
    }
  }
}
