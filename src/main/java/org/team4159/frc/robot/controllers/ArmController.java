package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc.robot.subsystems.Arm;
import org.team4159.lib.control.ControlLoop;

import static org.team4159.frc.robot.Constants.*;

public class ArmController implements ControlLoop {
  private enum State {
    IDLE,
    ZEROING,
    CLOSED_LOOP,
    ESTOP
  }
  private State state = State.IDLE;

  private Arm arm;

  private PIDController pid_controller;

  public ArmController(Arm arm) {
    this.arm = arm;

    pid_controller = new PIDController(
      ARM_CONSTANTS.kP,
      ARM_CONSTANTS.kI,
      ARM_CONSTANTS.kD
    );
    pid_controller.setTolerance(ARM_CONSTANTS.ACCEPTABLE_ERROR_IN_COUNTS);
  }

  @Override
  public void update() {
    SmartDashboard.putString("arm_state", state.toString());
    SmartDashboard.putNumber("arm_setpoint", getSetpoint());

    if (arm.isLimitSwitchClosed()) {
      arm.zeroEncoder();
    }

    switch (state) {
      case IDLE:
        break;
      case ZEROING:
        if (arm.isLimitSwitchClosed()) {
          state = State.CLOSED_LOOP;
        } else {
          arm.setRawSpeed(ARM_CONSTANTS.ZEROING_SPEED);
        }
        break;
      case CLOSED_LOOP:
        double output = pid_controller.calculate(arm.getPosition());
        SmartDashboard.putNumber("arm_pid", output);
        arm.setRawVoltage(output);
        break;
    }
  }

  public boolean isAtSetpoint() {
    return pid_controller.atSetpoint();
  }

  public void startZeroing() {
    state = State.ZEROING;
  }

  public void setSetpoint(int setpoint) {
    pid_controller.setSetpoint(setpoint);
  }

  public int getSetpoint() {
    return (int) pid_controller.getSetpoint();
  }
}
