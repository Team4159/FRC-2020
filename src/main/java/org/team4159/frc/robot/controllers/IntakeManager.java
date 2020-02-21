package org.team4159.frc.robot.controllers;

import org.team4159.frc.robot.subsystems.Arm;
import org.team4159.frc.robot.subsystems.Feeder;
import org.team4159.frc.robot.subsystems.Intake;

import static org.team4159.frc.robot.Constants.*;

public class IntakeManager {
  private enum State {
    STOWED, // arm / intake is stowing / stowed
    DEPLOYING, // arm is deploying, intake is not being run
    INTAKING // arm is deployed, intake is being run
  }
  private State state;

  private Arm arm;
  private Intake intake;
  private Feeder feeder;

  public IntakeManager(Arm arm, Intake intake, Feeder feeder) {
    this.arm = arm;
    this.intake = intake;
    this.feeder = feeder;
  }

  public void update() {
    int arm_setpoint = ARM_CONSTANTS.UP_POSITION;
    double intake_speed = 0,
           feeder_speed = 0;

    switch (state) {
      case STOWED:
        break;
      case DEPLOYING:
        arm_setpoint = ARM_CONSTANTS.DOWN_POSITION;
        if (arm.isAtSetpoint()) {
          state = State.INTAKING;
        }
        break;
      case INTAKING:
        arm_setpoint = ARM_CONSTANTS.DOWN_POSITION;
        intake_speed = INTAKE_CONSTANTS.INTAKE_SPEED;
        feeder_speed = FEEDER_CONSTANTS.FEEDING_SPEED;
        break;
    }

    arm.setSetpoint(arm_setpoint);
    intake.setRawSpeed(intake_speed);
    feeder.setRawSpeed(feeder_speed);
  }

  public void intake() {
    if (state == State.STOWED)
      state = State.DEPLOYING;
  }

  public void stopIntaking() {
    state = State.STOWED;
  }
}
