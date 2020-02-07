import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import org.team4159.frc.robot.Trajectories;
import org.team4159.lib.CsvWriter;

import java.util.List;

import static org.team4159.frc.robot.Constants.*;

public class RamseteControllerTest {
  @Rule
  public TestName name = new TestName();

  private CsvWriter csv_writer;
  private Pose2d robot_pose;

  private final Trajectory test_trajectory = Trajectories.GO_FORWARD_ONE_METER;

  private RamseteController controller;

  private DifferentialDriveWheelSpeeds toWheelSpeeds(ChassisSpeeds chassis_speeds) {
    return new DifferentialDriveWheelSpeeds(
      chassis_speeds.vxMetersPerSecond - DRIVE_CONSTANTS.TRACK_WIDTH / 2
        * chassis_speeds.omegaRadiansPerSecond,
      chassis_speeds.vxMetersPerSecond + DRIVE_CONSTANTS.TRACK_WIDTH / 2
        * chassis_speeds.omegaRadiansPerSecond
    );
  }

  @Before
  public void reset() {
    csv_writer = new CsvWriter(name.getMethodName());
    robot_pose = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(0.0));

    controller = new RamseteController();
  }

  @Test
  public void RamseteWantsToMoveStraightForward() {
    final List<Trajectory.State> states = test_trajectory.getStates();

    double sigma_differences = 0;

    for (Trajectory.State state : states) {
      final ChassisSpeeds chassis_speeds = controller.calculate(robot_pose, state);
      final DifferentialDriveWheelSpeeds wheel_speeds = toWheelSpeeds(chassis_speeds);

      double x_velocity = chassis_speeds.vxMetersPerSecond,
                   y_velocity = chassis_speeds.vyMetersPerSecond,
                   angular_velocity = chassis_speeds.omegaRadiansPerSecond,
                   left_wheel_speeds = wheel_speeds.leftMetersPerSecond,
                   right_wheel_speeds = wheel_speeds.rightMetersPerSecond;

      sigma_differences += Math.abs(left_wheel_speeds - right_wheel_speeds);

      csv_writer.write(x_velocity, y_velocity, angular_velocity, left_wheel_speeds, right_wheel_speeds);
    }

    csv_writer.close();

    Assert.assertEquals(0, sigma_differences, 0.01);
  }

  @Test
  public void RamseteWantsToMoveStraightForwardWhileRobotIsMovingForward() {
    final List<Trajectory.State> states = test_trajectory.getStates();

    double sigma_differences = 0;

    double i = 0;

    for (Trajectory.State state : states) {
      i += 0.1;
      robot_pose = new Pose2d(i, 0, Rotation2d.fromDegrees(0));

      final ChassisSpeeds chassis_speeds = controller.calculate(robot_pose, state);
      final DifferentialDriveWheelSpeeds wheel_speeds = toWheelSpeeds(chassis_speeds);

      double x_velocity = chassis_speeds.vxMetersPerSecond,
        y_velocity = chassis_speeds.vyMetersPerSecond,
        angular_velocity = chassis_speeds.omegaRadiansPerSecond,
        left_wheel_speeds = wheel_speeds.leftMetersPerSecond,
        right_wheel_speeds = wheel_speeds.rightMetersPerSecond;

      sigma_differences += Math.abs(left_wheel_speeds - right_wheel_speeds);

      csv_writer.write(x_velocity, y_velocity, angular_velocity, left_wheel_speeds, right_wheel_speeds);
    }

    csv_writer.close();

    Assert.assertEquals(0, sigma_differences, 0.01);
  }

  @Test
  public void RamseteWantsToTurn() {
    final List<Trajectory.State> states = test_trajectory.getStates();

    double sigma_differences = 0;

    robot_pose = new Pose2d(0.0, 0.0, Rotation2d.fromDegrees(90.0));

    for (Trajectory.State state : states) {
      final ChassisSpeeds chassis_speeds = controller.calculate(robot_pose, state);
      final DifferentialDriveWheelSpeeds wheel_speeds = toWheelSpeeds(chassis_speeds);

      double x_velocity = chassis_speeds.vxMetersPerSecond,
        y_velocity = chassis_speeds.vyMetersPerSecond,
        angular_velocity = chassis_speeds.omegaRadiansPerSecond,
        left_wheel_speeds = wheel_speeds.leftMetersPerSecond,
        right_wheel_speeds = wheel_speeds.rightMetersPerSecond;

      sigma_differences += left_wheel_speeds - right_wheel_speeds;

      csv_writer.write(x_velocity, y_velocity, angular_velocity, left_wheel_speeds, right_wheel_speeds);
    }

    csv_writer.close();

    // Should be greater than zero if turning left

    Assert.assertTrue(sigma_differences > 0);
  }
}
