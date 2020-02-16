package org.team4159.frc.robot.commands.feeder;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Feeder;

public class RunFeeder extends CommandBase {
  private Feeder feeder;

  private Timer timer = new Timer();
  private double duration;

  public RunFeeder(double duration_seconds, Feeder feeder) {
    this.duration = duration_seconds;
    this.feeder = feeder;

    addRequirements(feeder);
  }

  @Override
  public void initialize() {
    timer.start();
  }

  @Override
  public void execute() {
    feeder.feed();
  }

  @Override
  public boolean isFinished() {
    return timer.hasPeriodPassed(duration);
  }

  @Override
  public void end(boolean interrupted) {
    feeder.stop();
  }
}
