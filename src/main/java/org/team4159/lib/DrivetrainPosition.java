package org.team4159.lib;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import org.team4159.frc.robot.subsystems.Drivetrain;

public class DrivetrainPosition {
  private Pose2d position;

  private double prev_magnitude;

  public DrivetrainPosition(Pose2d current_position) {
    position = current_position;
  }

  public Pose2d getPose() {
    return position;
  }

  public void update(double current_magnitude, double current_heading) {
    double magnitude = current_magnitude - prev_magnitude;
    prev_magnitude = current_magnitude;

    double dx = magnitude * Math.cos(current_heading);
    double dy = magnitude * Math.sin(current_heading);

    position = new Pose2d(dx + position.getTranslation().getX(),
                          dy + position.getTranslation().getY(),
                          Rotation2d.fromDegrees(current_heading));
  }

  public void update(double left, double right, double current_heading) {
    update((left + right) / 2.0, current_heading);
  }

  public void set(Pose2d reset_position) {
    position = reset_position;
  }
}
