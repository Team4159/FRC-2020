package org.team4159.frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Arm;

public class MoveArm extends CommandBase {
  private Arm arm;

  private int setpoint;

  public MoveArm(Arm arm, int setpoint) {
    this.arm = arm;
    this.setpoint = setpoint;
  }

  @Override
  public void initialize() {
    arm.enable();
    arm.setSetpoint(setpoint);
  }

  @Override
  public boolean isFinished() {
    return arm.getController().atSetpoint();
  }
}
