package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import org.team4159.frc.robot.subsystems.IArm;
import org.team4159.lib.control.IControlLoop;

import static org.team4159.frc.robot.Constants.*;

public class ArmController implements IControlLoop {
  private enum State {
    ZEROING,
    CLOSED_LOOP,
    ESTOP
  }
  private State state = State.ZEROING;

  private IArm arm;

  private PIDController pid_controller;

  public ArmController(IArm arm) {
    this.arm = arm;

    pid_controller = new PIDController(
      ARM_CONSTANTS.kP,
      ARM_CONSTANTS.kI,
      ARM_CONSTANTS.kD
    );
    pid_controller.setTolerance(ARM_CONSTANTS.ACCEPTABLE_ERROR_IN_COUNTS);
  }

  public void update() {
    switch (state) {
      case ZEROING:
        arm.setRawSpeed(ARM_CONSTANTS.ZEROING_SPEED);
        if (arm.isLimitSwitchClosed()) {
          arm.zeroEncoder();
          state = State.CLOSED_LOOP;
        }
        break;
      case CLOSED_LOOP:
        double output = pid_controller.calculate(arm.getPosition());
        arm.setRawVoltage(output);
        break;
    }
  }

  public boolean isAtSetpoint() {
    return pid_controller.atSetpoint();
  }

  public void setSetpoint(int setpoint) {
    pid_controller.setSetpoint(setpoint);
  }

  public int getSetpoint() {
    return (int) pid_controller.getSetpoint();
  }
}
