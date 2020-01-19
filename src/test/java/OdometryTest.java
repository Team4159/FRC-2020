import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.team4159.lib.CsvWriter;
import org.team4159.lib.Odometry;

public class OdometryTest {
  private final CsvWriter csv_writer = new CsvWriter("odometry_test");

  private Odometry odometry;

  private double distance;
  private double direction;

  @Before
  public void reset() {
    distance = 0.0;
    direction = 0.0;

    odometry = new Odometry();
  }

  @Test
  public void GoesForwardOneMeter() {
    for (int i = 0; i < 100; i++) {
      distance += 0.01;
      odometry.update(distance, direction);

      final Pose2d current_pose = odometry.getPose();
      csv_writer.write(current_pose.getTranslation().getX(),
        current_pose.getTranslation().getY(),
        current_pose.getRotation().getDegrees());
    }

    csv_writer.finish();

    final Pose2d final_pose = odometry.getPose();

    final double final_x = final_pose.getTranslation().getX();
    final double final_y = final_pose.getTranslation().getY();
    final double final_direction = final_pose.getRotation().getDegrees();

    Assert.assertEquals("Expected Final X: 1.0, Output Final X: " + final_x, 1.0, final_x, 0.1);
    Assert.assertEquals("Expected Final Y: 0.0, Output Final Y: " + final_y, 0.0, final_y, 0.1);
    Assert.assertEquals("Expected Final Direction: 0.0, Output Final Direction: " + final_direction, 0.1, final_direction, 0.1);
  }

  @Test
  public void GoesDiagonallyOneMeter() {
    direction = 45.0;

    for (int i = 0; i < 100; i++) {
      distance += 0.01;
      odometry.update(distance, direction);

      final Pose2d current_pose = odometry.getPose();
      csv_writer.write(current_pose.getTranslation().getX(),
        current_pose.getTranslation().getY(),
        current_pose.getRotation().getDegrees());
    }

    csv_writer.finish();

    final Pose2d final_pose = odometry.getPose();

    final double final_x = final_pose.getTranslation().getX();
    final double final_y = final_pose.getTranslation().getY();
    final double final_direction = final_pose.getRotation().getDegrees();

    Assert.assertEquals("Expected Final X: 0.707, Output Final X: " + final_x, Math.sqrt(2.0) / 2.0, final_x, 0.1);
    Assert.assertEquals("Expected Final Y: 0.707, Output Final Y: " + final_y, Math.sqrt(2.0) / 2.0, final_y, 0.1);
    Assert.assertEquals("Expected Final Direction: 45.0, Output Final Direction: " + final_direction, 0.1, 45.0, 0.1);
  }
}

