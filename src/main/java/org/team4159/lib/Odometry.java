package org.team4159.lib;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;

/**
 * Tracks a physical object and its heading (i.e. a Drivetrain) on a Cartesian x, y plane, when fed encoder and gyro values.
 */
public class Odometry {
  private Pose2d position;

  private double prev_magnitude;

  public Odometry(Pose2d current_position) {
    position = current_position;
  }

  public Odometry() {
    this(new Pose2d());
  }

  public Pose2d getPose() {
    return position;
  }

  public void update(double current_magnitude, double current_heading) {
    double magnitude = current_magnitude - prev_magnitude;
    prev_magnitude = current_magnitude;

    double dx = magnitude * Math.cos(current_heading);
    double dy = magnitude * Math.sin(current_heading);

<<<<<<< HEAD
    Pose2d new_position = new Pose2d(dx + position.getTranslation().getX(),
                          dy + position.getTranslation().getY(),
                          Rotation2d.fromDegrees(current_heading));

    position = new_position;
=======
    position = new Pose2d(dx + position.getTranslation().getX(),
                          dy + position.getTranslation().getY(),
                          Rotation2d.fromDegrees(current_heading));
>>>>>>> d1c14f4eb02defe26bb3761b4d62d2c2d79c45e2
  }

  public void update(double left, double right, double current_heading) {
    update((left + right) / 2.0, current_heading);
  }

  public void set(Pose2d reset_position) {
    position = reset_position;
  }
}
