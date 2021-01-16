package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class TurretController implements ControlLoop {
  private enum State {
    //ZEROING,
    //SEEKING_TARGET,
    //FOUND_TARGET,
    //RECOVERING,
    IDLE,
    MANUAL
  }
  private State last_state;
  private State state = State.MANUAL;

  private Turret turret;
  private Limelight limelight;

  private double demanded_signal = 0.0;

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
    position_pid.setSetpoint(0);
  }

  @Override
  public void update() {
    SmartDashboard.putString("turret_state", state.toString());
    SmartDashboard.putNumber("turret_position", turret.getPosition());
    SmartDashboard.putBoolean("turret_forward_lim_switch", turret.isForwardLimitSwitchClosed());
    SmartDashboard.putBoolean("turret_reverse_lim_switch", turret.isReverseLimitSwitchClosed());

    if (turret.isForwardLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.FORWARD_POSITION);
    } else if (turret.isReverseLimitSwitchClosed()) {
      turret.setEncoderPosition(TURRET_CONSTANTS.REVERSE_POSITION);
    }

    /*
    if (isTurretOutOfSafeRange() && state != State.ZEROING) {
      setState(State.RECOVERING);
    }
     */

    // Things to debug/test:
    // 1. Why is it seeking when seeking is commented out? (Check if the limelight latches on to anything else).
    // 2. Tune by incrementing D and lowering P.
    // 3. Test the shooter.
    // 4. Try to implement seeking?
    turret.setRawSpeed(clamp(demanded_signal));
  }

  public double clamp(double doub) {
    return Math.min(0.075, Math.max(-0.075, doub));
  }

  public boolean isTurretPointingAtTarget() {
    //return true;
    return aim_pid.atSetpoint();
  }

  public void demand(double signal) {
    demanded_signal = signal;
  }

  /*
  public boolean isTurretOutOfSafeRange() {
    return Math.abs(turret.getPosition()) < TURRET_CONSTANTS.SAFE_FORWARD_POSITION;
  }
  */

//  public void startZeroing() {
//    state = State.ZEROING;
//  }

//  public void startSeeking() {
//    if (state == State.FOUND_TARGET) return;
//    limelight.setLEDMode(Limelight.LEDMode.ForceOn);
////    seeking_range = TURRET_CONSTANTS.STARTING_SEEKING_RANGE;
////    seeking_starting_position = turret.getPosition();
//    state = State.SEEKING_TARGET;
//  }

//  private void foundTarget() {
//    state = State.FOUND_TARGET;
//    System.out.println("YES 0" + state);
//    aim_pid.reset();
//  }

  /*
  public void startRecovering() {
    last_state = state;
    state = State.RECOVERING;
  }
  */

  public void idle() {
      state = State.IDLE;
  }
}
