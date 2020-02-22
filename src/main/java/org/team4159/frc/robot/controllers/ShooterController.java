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


  private Shooter shooter;
  private Limelight limelight;

  private State state = State.IDLE;

  private PIDController pid_controller = new PIDController(
    SHOOTER_CONSTANTS.kP,
    SHOOTER_CONSTANTS.kI,
    SHOOTER_CONSTANTS.kD
  );

  public ShooterController(Shooter shooter) {
    this.shooter = shooter;
    this.limelight = Limelight.getDefault();

    pid_controller.setTolerance(SHOOTER_CONSTANTS.ACCEPTABLE_SPEED_ERROR);
  }

  @Override
  public void update() {
    switch (state) {
      case IDLE:
        shooter.setRawVoltage(0);
        break;
      case CLOSED_LOOP:
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

        shooter.setRawVoltage(pid_controller.calculate(shooter.getSpeed()));
        break;
    }
  }

  public double getDistanceToVisionTarget() {
    double vertical_offset = limelight.getTargetVerticalOffset();
    double total_angle_to_target = LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset;
    return LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT / Math.tan(total_angle_to_target);
  }

  public boolean isAtTargetSpeed() {
    return pid_controller.atSetpoint();
  }

  // Temporary for development?
  public void writeToSmartDashboard() {
    SmartDashboard.putNumber("target_shooter_speed", 0);
    SmartDashboard.putNumber("shooter_kp", SHOOTER_CONSTANTS.kP);
    SmartDashboard.putNumber("shooter_ki", SHOOTER_CONSTANTS.kI);
    SmartDashboard.putNumber("shooter_kd", SHOOTER_CONSTANTS.kD);


    SmartDashboard.putNumber(
      "current_shooter_speed_rpm",
      shooter.getSpeed() * SHOOTER_CONSTANTS.COUNTS_PER_SECOND_TO_RPM
    );

    double distance = getDistanceToVisionTarget();

    SmartDashboard.putNumber("distance in inches", distance);
    SmartDashboard.putNumber("distance in feet", distance / 12);
  }

  public void setState(State state) {
    this.state = state;
  }

  public State getState() {
    return state;
  }
}
