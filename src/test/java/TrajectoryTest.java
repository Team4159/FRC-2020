import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.team4159.frc.robot.Trajectories;
import org.team4159.lib.logging.CSVWriter;
import org.team4159.lib.math.Epsilon;

public class TrajectoryTest {
  @Rule
  public TestName name = new TestName();

  private File temp_file;
  private CSVWriter csv_writer;

  private final Trajectory test_trajectory = Trajectories.TEST_TRAJECTORY;

  @Before
  public void reset() {
    try {
      temp_file = File.createTempFile(name.getMethodName(), ".csv");
      csv_writer = new CSVWriter(File.createTempFile(name.getMethodName(), ".csv"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void TestTrajectoryPath() {
    final List<Trajectory.State> states = test_trajectory.getStates();

    for (Trajectory.State state : states) {
      csv_writer.write(state.poseMeters.getTranslation().getX(),
                       state.poseMeters.getTranslation().getY(),
                       state.poseMeters.getRotation().getDegrees());
    }

    System.out.println(temp_file);
    csv_writer.close();

    final Pose2d final_pose = states.get(states.size() - 1).poseMeters;

    final double final_x = final_pose.getTranslation().getX();
    final double final_y = final_pose.getTranslation().getY();
    final double final_direction = final_pose.getRotation().getDegrees();

    Assert.assertEquals("Expected Final X: 0.0, Output Final X: " + final_x, 0.0, final_x, Epsilon.kEpsilon);
    Assert.assertEquals("Expected Final Y: 1.0, Output Final Y: " + final_y, 1.0, final_y, Epsilon.kEpsilon);
    Assert.assertEquals("Expected Final Direction: 90.0, Output Final Direction: " + final_direction, 0.0, final_direction, 1.0);
  }
}
