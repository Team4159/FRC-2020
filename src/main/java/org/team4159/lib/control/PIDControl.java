package org.team4159.lib.control;

import edu.wpi.first.wpilibj.TimedRobot;

public class PIDControl {
  private double kP, kI, kD, interval, max_integral;

  private double error = 0.0;
  private double delta_error = 0.0;
  private double last_error = 0.0;
  private double sigma_error = 0.0;
  private double goal = 0.0;

  public PIDControl(double kP, double kI, double kD) {
    this(kP, kI, kD, TimedRobot.kDefaultPeriod, Double.POSITIVE_INFINITY);
  }

  public PIDControl(double kP, double kI, double kD, double interval) {
    this(kP, kI, kD, interval, Double.POSITIVE_INFINITY);
  }

  public PIDControl(double kP, double kI, double kD, double dt, double max_integral) {
    setPID(kP, kI, kD);
    setInterval(dt);
    setMaxIntegral(max_integral);
  }

  public double calculateOutput(double position) {
    error = goal - position;
    delta_error = (error - last_error) / interval;
    sigma_error = Math.max(-max_integral, Math.min(max_integral, sigma_error + (error * interval)));

    final double output =
      kP * error +
      kI * sigma_error +
      kD * delta_error;

    last_error = error;

    return(output);
  }

  public double getError() {
    return error;
  }

  public double getErrorSum() {
    return sigma_error;
  }

  public double getErrorChange() {
    return delta_error;
  }

  public double getGoal() {
    return goal;
  }

  public void setGoal(double goal) {
    this.goal = goal;
  }

  public void setPID(double kP, double kI, double kD) {
    setP(kP);
    setI(kI);
    setD(kD);
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

  public void setInterval(double interval) {
    this.interval = interval;
  }

  public void setMaxIntegral(double max_integral) {
    this.max_integral = Math.abs(max_integral);
  }

  public void resetError() {
    last_error = 0.0;
    sigma_error = 0.0;
  }
}
