package org.team4159.frc.robot;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Test;

import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class TestTrajectories {
  @Test
  public void TestTrajectoryGeneration() {
    Field[] fields = Trajectories.class.getFields();
    for (Field field : fields) {
      try {
        Trajectory trajectory = (Trajectory) field.get(new Trajectories());
      } catch (IllegalAccessException | ExceptionInInitializerError e) {
        e.printStackTrace();
        // Assert.fail();
      }
    }
  }
}
