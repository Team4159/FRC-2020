package org.team4159.frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;

import org.team4159.frc.robot.subsystems.Arm;

import static org.team4159.frc.robot.Constants.*;

public class ToggleArm extends ConditionalCommand {
  public ToggleArm(Arm arm) {
    super(
      new MoveArm(arm, ARM_CONSTANTS.DOWN_POSITION),
      new MoveArm(arm, ARM_CONSTANTS.UP_POSITION),
      () -> arm.getSetpoint() == ARM_CONSTANTS.UP_POSITION
    );
  }
}
