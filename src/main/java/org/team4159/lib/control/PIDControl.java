package org.team4159.lib.control;

public class PIDControl {
  private double kP;
  private double kI;
  private double kD;

  private double last_error = 0.0;
  private double error_sum = 0.0;
  private double goal = 0.0;

  public PIDControl(double kP, double kI, double kD) {
    setPID(kP, kI, kD);
  }

  public double calculateOutput(double position) {
    final double error = goal - position;
    final double delta_error = error - last_error;
    error_sum += error;

    final double output =
      kP * error +
      kI * error_sum +
      kD * delta_error;

    last_error = error;

    return(output);
  }

  public double getGoal() {
    return goal;
  }

  public void setGoal(double goal) {
    this.goal = goal;
  }

  public void setPID(double kP, double kI, double kD) {
    this.kP = kP;
    this.kI = kI;
    this.kD = kD;
  }

  public void setP(double kP) {
    this.kP = kP;
  }

  public void setI(double kI) {
    this.kI = kI;
  }

  public void setD(double kD) {
    this.kD = kD;
  }

  public void resetError() {
    last_error = 0.0;
    error_sum = 0.0;
  }
}
