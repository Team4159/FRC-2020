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
    resetPID(control);
  }

  public abstract double getPosition();
  public abstract void setOutput(double output);

  @Override
  public void periodic() {
    if (isSubsystemZeroed()) {
      setOutput(pid_control.calculateOutput(getPosition()));
    }
  }

  public double getError() {
    return pid_control.getError();
  }

  public double getErrorSum() {
    return pid_control.getErrorSum();
  }

  public double getErrorChange() {
    return pid_control.getErrorChange();
  }

  public double getGoal() {
    return pid_control.getGoal();
  }

  public void setGoal(double goal) {
    pid_control.setGoal(goal);
  }

  public void setInterval(double interval) {
    pid_control.setInterval(interval);
  }

  public void setMaxIntegral(double max_integral) {
    pid_control.setMaxIntegral(max_integral);
  }

  public void resetPID(PIDControl control) {
    pid_control = control;
  }

  public void setP(double kP) {
    pid_control.setP(kP);
  }

  public void setI(double kI) {
    pid_control.setI(kI);
  }

  public void setD(double kD) {
    pid_control.setD(kD);
  }
}
