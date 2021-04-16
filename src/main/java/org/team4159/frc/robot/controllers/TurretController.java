package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class TurretController implements ControlLoop {
  private enum State {
    IDLE,
    ZEROING,
    // STABLE = DONE ZEROING, sticky at position 0
    STABLE,
    SEEKING_TARGET,
    FOUND_TARGET,
    MANUAL
    //RECOVERING,
  }
  private State last_state;
  private State state = State.IDLE;

  private double manual_speed = 0;
  private double stable_setpoint = TURRET_CONSTANTS.CENTER_POSITION;

  private Turret turret;
  private Limelight limelight;


  private int seeking_direction, seeking_range, seeking_starting_position;

  private PIDController position_pid = new PIDController(
    TURRET_CONSTANTS.POSITION_kP,
    TURRET_CONSTANTS.POSITION_kI,
    TURRET_CONSTANTS.POSITION_kD
  );
  private PIDController aim_pid = new PIDController(
    TURRET_CONSTANTS.LIMELIGHT_AIM_kP,
    TURRET_CONSTANTS.LIMELIGHT_AIM_kI,
    TURRET_CONSTANTS.LIMELIGHT_AIM_kD
  );

  public TurretController(Turret turret, Limelight limelight) {
    this.turret = turret;
    this.limelight = limelight;
    turret.setEncoderPosition(0);
    position_pid.setSetpoint(0);
  }

  @Override
  public void update() {
    SmartDashboard.putString("turret_state", state.toString());
    SmartDashboard.putNumber("turret_position", turret.getPosition());
    SmartDashboard.putBoolean("turret_forward_lim_switch", turret.isForwardLimitSwitchClosed());
    SmartDashboard.putBoolean("turret_reverse_lim_switch", turret.isReverseLimitSwitchClosed());
    SmartDashboard.putNumber("turret_setpoint", position_pid.getSetpoint());

    if (turret.isForwardLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.FORWARD_POSITION);
    } else if (turret.isReverseLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.REVERSE_POSITION);
    }

    double speed = 0.0;

    switch (state) {
      case IDLE:
        break;
      case ZEROING:
        if (turret.isForwardLimitSwitchClosed()) {
          state = State.STABLE;
          stable_setpoint = TURRET_CONSTANTS.CENTER_POSITION;
        } else {
          speed = TURRET_CONSTANTS.ZEROING_SPEED;
        }
        break;
      case STABLE:
        position_pid.setSetpoint(stable_setpoint);
        speed = position_pid.calculate(turret.getPosition());
        break;
      case MANUAL:
        speed = manual_speed;
        stable_setpoint = turret.getPosition();
        break;
    }

    SmartDashboard.putNumber("turret_speed", speed);
    
    turret.setRawSpeed(speed);

//    switch (state) {
//      case IDLE:
//        // turret.setRawSpeed(0);
//        SmartDashboard.putNumber("turret_pid", position_pid.calculate(turret.getPosition()));
//        turret.setRawSpeed(position_pid.calculate(turret.getPosition()));
//        break;
//      case ZEROING:
//        if (turret.isForwardLimitSwitchClosed()) {
//          position_pid.setSetpoint(0);
//          state = State.IDLE;
//        } else {
//          turret.setRawSpeed(TURRET_CONSTANTS.ZEROING_SPEED);
//        }
//        break;
//      case SEEKING_TARGET:
//        if (!limelight.isTargetVisible()) {
//          //turret.setRawSpeed(0);
//          turret.setRawSpeed(seeking_direction * TURRET_CONSTANTS.SEEKING_SPEED);
//
//          if (Math.abs(turret.getPosition() - seeking_starting_position) > seeking_range) {
//            seeking_direction *= -1;
//
//            int new_seeking_range = seeking_range + TURRET_CONSTANTS.SEEKING_RANGE_INCREMENT;
//
//            if (!(turret.getPosition() + new_seeking_range > TURRET_CONSTANTS.SAFE_FORWARD_POSITION
//                    && turret.getPosition() - new_seeking_range < TURRET_CONSTANTS.SAFE_REVERSE_POSITION)) {
//              seeking_range = new_seeking_range;
//            }
//          }
//        } else {
//          foundTarget();
//        }
//        break;
//      case FOUND_TARGET:
//        if (limelight.isTargetVisible()) {
//          SmartDashboard.putNumber("aim_pid", aim_pid.calculate(limelight.getTargetHorizontalOffset()));
//          //turret.setRawSpeed(aim_pid.calculate(limelight.getTargetHorizontalOffset()));
//          // Temporary fix
//          turret.setRawSpeed(Math.min(0.075, Math.max(-0.075, aim_pid.calculate(limelight.getTargetHorizontalOffset()))));
//        } else {
//          startSeeking();
//        }
//        break;
//      /*
//      case RECOVERING:
//        if (turret.getPosition() > TURRET_CONSTANTS.SAFE_FORWARD_POSITION) {
//          turret.setRawSpeed(TURRET_CONSTANTS.ZEROING_SPEED);
//        } else if (turret.getPosition() < TURRET_CONSTANTS.SAFE_REVERSE_POSITION) {
//          turret.setRawSpeed(-1.0 * TURRET_CONSTANTS.ZEROING_SPEED);
//        } else {
//          setState(last_state);
//        }
//        break;
//      */
//    }
  }

  public boolean isTurretPointingAtTarget() {
    //return true;
    return aim_pid.atSetpoint();
  }

  /*
  public boolean isTurretOutOfSafeRange() {
    return Math.abs(turret.getPosition()) < TURRET_CONSTANTS.SAFE_FORWARD_POSITION;
  }
  */

  public void startZeroing() {
    state = State.ZEROING;
  }

  public void startSeeking() {
    if (state == State.FOUND_TARGET) return;
    if (state == State.SEEKING_TARGET) return;
    state = State.SEEKING_TARGET;

    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
    seeking_range = TURRET_CONSTANTS.INITIAL_SEEKING_RANGE;
    seeking_starting_position = turret.getPosition();
    seeking_direction = 1;
  }

  private void foundTarget() {
    state = State.FOUND_TARGET;
    aim_pid.reset();
  }

  /*
  public void startRecovering() {
    last_state = state;
    state = State.RECOVERING;
  }
  */

  public void stable() {
    if (state != State.ZEROING) {
      state = State.STABLE;
    }
  }

  public void idle() {
    if (state != State.ZEROING) {
      state = State.IDLE;
    }
  }

  public void manual(double speed) {
    if (state != State.ZEROING) {
      state = State.MANUAL;
      manual_speed = speed;
    }
  }
}
