package org.team4159.frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.InstantCommand;

import org.team4159.frc.robot.subsystems.Arm;

import static org.team4159.frc.robot.Constants.*;

public class ToggleArm extends InstantCommand {
  private Arm arm;

  public ToggleArm(Arm arm) {
    this.arm = arm;
    addRequirements(arm);
  }

  @Override
  public void initialize() {
    if (arm.getController().getSetpoint() == ARM_CONSTANTS.UP_POSITION) {
      arm.lowerIntake();
    } else {
      arm.raiseIntake();
    }
  }
}
