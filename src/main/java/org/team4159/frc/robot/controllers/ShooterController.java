package org.team4159.frc.robot.controllers;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.lib.control.ControlLoop;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class ShooterController implements ControlLoop {
  public enum State {
    IDLE,
    CLOSED_LOOP
  }
  private State state = State.IDLE;

  private Shooter shooter;
  private Limelight limelight;

  private PIDController pid_controller = new PIDController(
    SHOOTER_CONSTANTS.kP,
    SHOOTER_CONSTANTS.kI,
    SHOOTER_CONSTANTS.kD
  );

  public ShooterController(Shooter shooter, Limelight limelight) {
    this.shooter = shooter;
    this.limelight = limelight;

    pid_controller.setTolerance(SHOOTER_CONSTANTS.ACCEPTABLE_SPEED_ERROR);
  }

  @Override
  public void update() {
    SmartDashboard.putNumber("shooter_rpm", shooter.getSpeed());
    SmartDashboard.putNumber("target_shooter_rpm", getTargetSpeed());
    SmartDashboard.putNumber("vision_target_dist", getDistanceToVisionTarget());
    switch (state) {
      case IDLE:
        shooter.setRawVoltage(0);
        break;
      case CLOSED_LOOP:
        /*
        if (limelight.isTargetVisible()) {
          // In theory, the velocity of the ball should scale linearly with RPM, which in turn scales linearly with horizontal distance (I think)
          // I'll update this math later
          double distance = getDistanceToVisionTarget();
          double k = 1;
          // Y Intercept measured empirically
          double b = 0;
          double target_rpm = distance * k + b;

          pid_controller.setSetpoint(target_rpm);
        }
        */

        shooter.setRawVoltage(-1 * pid_controller.calculate(shooter.getSpeed()));
        break;
    }
  }

  public double getDistanceToVisionTarget() {
    double vertical_offset = limelight.getTargetVerticalOffset();
    double total_angle_to_target = LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset;
    SmartDashboard.putNumber("tan_angle", Math.tan(total_angle_to_target));
    SmartDashboard.putNumber("vis_height", LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT);
    return LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT / Math.tan(Math.toRadians(total_angle_to_target));
  }

  public double getTargetSpeed() {
    return pid_controller.getSetpoint();
  }

  public void setTargetSpeed(double speed) {
    pid_controller.setSetpoint(speed);
  }

  public boolean isAtTargetSpeed() {
    return pid_controller.atSetpoint();
  }

  public void setState(State state) {
    this.state = state;
  }

  public void spinUp() {
    state = State.CLOSED_LOOP;
  }

  public void spinDown() {
    state = State.IDLE;
  }
}
