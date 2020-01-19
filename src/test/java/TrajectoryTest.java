import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.junit.Assert;
import org.junit.Test;

import org.team4159.frc.robot.Trajectories;
import org.team4159.lib.CsvWriter;

import java.util.List;

public class TrajectoryTest {
  private final CsvWriter csv_writer = new CsvWriter("trajectory_test");

  private final Trajectory test_trajectory = Trajectories.TEST_TRAJECTORY;

  @Test
  public void TestTrajectory() {
    final List<Trajectory.State> states = test_trajectory.getStates();

    for (Trajectory.State state : states) {
      csv_writer.write(state.poseMeters.getTranslation().getX(),
                       state.poseMeters.getTranslation().getY(),
                       state.poseMeters.getRotation().getDegrees());
    }

    csv_writer.finish();

    final Pose2d final_pose = states.get(states.size() - 1).poseMeters;

    final double final_x = final_pose.getTranslation().getX();
    final double final_y = final_pose.getTranslation().getY();
    final double final_direction = final_pose.getRotation().getDegrees();

    Assert.assertEquals("Expected Final X: 0.0, Output Final X: " + final_x, 0.0, final_x, 0.1);
    Assert.assertEquals("Expected Final Y: 3.0, Output Final Y: " + final_y, 3.0, final_y, 0.1);
    Assert.assertEquals("Expected Final Direction: 90.0, Output Final Direction: " + final_direction, 90.0, final_direction, 1.0);
  }
}
