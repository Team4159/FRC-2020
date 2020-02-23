package org.team4159.frc.robot;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.first.wpilibj.trajectory.Trajectory;
import org.team4159.lib.logging.CSVWriter;

public class TrajectoryTest {
  @Test
  public void TestTrajectoryGeneration() {
    Field[] fields = Trajectories.class.getFields();
    for (Field field : fields) {
      try {
        Trajectory trajectory = (Trajectory) field.get(new Trajectories());
        List<Trajectory.State> states = trajectory.getStates();

        CSVWriter writer = new CSVWriter("paths/" + field.getName() + ".csv");

        for (Trajectory.State state : states) {
          writer.write(
            state.timeSeconds,
            state.poseMeters.getTranslation().getX(),
            state.poseMeters.getTranslation().getY(),
            state.velocityMetersPerSecond,
            state.accelerationMetersPerSecondSq,
            state.curvatureRadPerMeter
          );
        }

        writer.close();
      } catch (IllegalAccessException | ExceptionInInitializerError e) {
        e.printStackTrace();
        Assert.fail();
      }
    }
  }
}
