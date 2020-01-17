package org.team4159.frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.team4159.frc.robot.subsystems.Drivetrain;

import static org.team4159.frc.robot.Constants.*;

public class BlueAuto extends SequentialCommandGroup {
  public BlueAuto(Drivetrain drivetrain) {
    addRequirements(drivetrain);

    addCommands(
            // start position to trench run
            new FollowTrajectory(TRAJECTORIES.TRENCH_RUN_BAL_TO_SHOOTING_POS, drivetrain)
            // shoot ball
    );
  }
}
