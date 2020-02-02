package org.team4159.lib.control;

public class PIDControl {
  private double kP;
  private double kI;
  private double kD;

  private double last_error = 0;
  private double error_sum = 0;
  private double setpoint = 0;

  public PIDControl(double P, double I, double D) {
    kP = P;
    kI = I;
    kD = D;
  }

  public void setGoal(double goal) {
    setpoint = goal;
  }

  public double calculateOutput(double position) {
    final double error = setpoint - position;
    final double delta_error = error - last_error;
    error_sum += error;

    final double output = kP * error + kI * error + kD * delta_error;

    last_error = error;

    return(output);
  }
}
