package org.team4159.frc.robot.controllers.complex;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team4159.frc.robot.controllers.ArmController;
import org.team4159.frc.robot.subsystems.Feeder;
import org.team4159.frc.robot.subsystems.Intake;
import org.team4159.lib.control.ControlLoop;

import static org.team4159.frc.robot.Constants.*;

public class IntakeController implements ControlLoop {
  private enum State {
    STOWED, // arm / intake is stowing / stowed
    DEPLOYING, // arm is deploying, intake is not being run
    INTAKING // arm is deployed, intake is being run
  }
  private State state = State.STOWED;

  private ArmController arm_controller;
  private Intake intake;
  private Feeder feeder;

  public IntakeController(ArmController arm_controller, Intake intake, Feeder feeder) {
    this.arm_controller = arm_controller;
    this.intake = intake;
    this.feeder = feeder;
  }

  @Override
  public void update() {
    int arm_setpoint = ARM_CONSTANTS.UP_POSITION;
    double intake_speed = 0,
           tower_speed = 0,
            floor_speed = 0;

    SmartDashboard.putString("intake_state", state.toString());

    switch (state) {
      case STOWED:
        break;
      case DEPLOYING:
        arm_setpoint = ARM_CONSTANTS.DOWN_POSITION;
        if (arm_controller.isAtSetpoint()) {
          state = State.INTAKING;
        }
        break;
      case INTAKING:
        arm_setpoint = ARM_CONSTANTS.DOWN_POSITION;
        // intake_speed = INTAKE_CONSTANTS.INTAKE_SPEED;
        tower_speed = FEEDER_CONSTANTS.TOWER_FEEDING_SPEED;
        floor_speed = FEEDER_CONSTANTS.FLOOR_FEEDING_SPEED;
        break;
    }

    arm_controller.setSetpoint(arm_setpoint);
    intake.setRawSpeed(intake_speed);
    feeder.setRawFloorSpeed(floor_speed);
    feeder.setRawTowerSpeed(tower_speed);
  }

  public void intake() {
    if (state == State.STOWED)
      state = State.DEPLOYING;
  }

  public void stopIntaking() {
    state = State.STOWED;
  }
}
