import edu.wpi.first.wpilibj.trajectory.Trajectory;

import org.junit.Test;

import org.team4159.frc.robot.Trajectories;

public class TrajectoryPrinter {
  private Trajectory trajectory = Trajectories.TEST_TRAJECTORY;

  @Test
  public void PrintTrajectory() {
    for (Trajectory.State state : trajectory.getStates()) {
      System.out.println(state.poseMeters.getTranslation().getX() + ", " + state.poseMeters.getTranslation().getY());
    }
  }
}
