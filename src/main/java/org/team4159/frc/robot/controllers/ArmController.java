package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc.robot.subsystems.Arm;
import org.team4159.lib.control.ControlLoop;

import static org.team4159.frc.robot.Constants.*;

public class ArmController implements ControlLoop {
  private enum State {
    IDLE,
    // ZEROING,
    CLOSED_LOOP,
    ESTOP
  }
  private State state = State.IDLE;

  private Arm arm;

  private PIDController pid_controller;

  public double output;

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
      case CLOSED_LOOP:
          output = pid_controller.calculate(arm.getPosition()); //Potentially Comment That Line Out
          SmartDashboard.putNumber("arm_pid", output);
          setRawVoltage(output); //And Maybe This Line
        break;
    }
  }

  public boolean isAtSetpoint() {
    return pid_controller.atSetpoint();
  }

  public void startZeroing() {
    state = State.CLOSED_LOOP;
    setSetpoint(0);
  }

  public void setSetpoint(int setpoint) {
    pid_controller.setSetpoint(setpoint);
  }

  public int getSetpoint() {
    return (int) pid_controller.getSetpoint();
  }

  public void setIdle() {
    state = State.Idle;
  }

  public void setRawVoltage(Double output) {
    arm.setRawVoltage(output);
  }
}
