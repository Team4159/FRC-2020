package org.team4159.frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Arm;

import static org.team4159.frc.robot.Constants.*;

public class ZeroArm extends CommandBase {
  private Arm arm;

  public ZeroArm(Arm arm) {
    this.arm = arm;
    addRequirements(arm);
  }

  @Override
  public void execute() {
    arm.setRawSpeed(ARM_CONSTANTS.ZEROING_SPEED);
  }

  @Override
  public boolean isFinished() {
    return arm.isLimitSwitchClosed();
  }

  @Override
  public void end(boolean interrupted) {
    arm.zeroEncoder();
  }
}
