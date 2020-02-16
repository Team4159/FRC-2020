package org.team4159.frc.robot.commands.intake;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team4159.frc.robot.subsystems.Intake;

public class RunIntake extends CommandBase {
  private double duration;
  private Intake intake;

  private Timer timer = new Timer();

  public RunIntake(double duration_seconds, Intake intake) {
    this.duration = duration_seconds;
    this.intake = intake;

    addRequirements(intake);
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    intake.intake();
  }

  @Override
  public boolean isFinished() {
    return timer.hasPeriodPassed(duration);
  }

  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }
}
