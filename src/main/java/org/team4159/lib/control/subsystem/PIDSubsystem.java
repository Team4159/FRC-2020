package org.team4159.lib.control.subsystem;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.lib.control.PIDControl;

public abstract class PIDSubsystem extends ZeroableSubsystem {
  protected PIDControl pid_control;

  public PIDSubsystem(double kP, double kI, double kD) {
    this(new PIDControl(kP, kI, kD, TimedRobot.kDefaultPeriod, Double.POSITIVE_INFINITY));
  }

  public PIDSubsystem(PIDControl control) {
    pid_control = control;
  }

  public abstract double getPosition();
  public abstract void setOutput(double output);

  @Override
  public void periodic() {
    if (isSubsystemZeroed()) {
      setOutput(pid_control.calculateOutput(getPosition()));
    }
  }
}
