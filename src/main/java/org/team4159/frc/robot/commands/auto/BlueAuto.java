package org.team4159.frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import edu.wpi.first.wpilibj2.command.WaitCommand;
import org.team4159.frc.robot.Trajectories;
import org.team4159.frc.robot.commands.arm.ToggleArm;
import org.team4159.frc.robot.commands.drivetrain.TurnDegrees;
import org.team4159.frc.robot.commands.feeder.RunFeeder;
import org.team4159.frc.robot.commands.intake.RunIntake;
import org.team4159.frc.robot.commands.turret.LimelightSeek;
import org.team4159.frc.robot.subsystems.*;

/*
 * Placeholder until we plan out an autonomous routine
 */
public class BlueAuto extends SequentialCommandGroup {
  public BlueAuto(Drivetrain drivetrain, Arm arm, Feeder feeder, Intake intake, Turret turret, Shooter shooter) {
    addRequirements(drivetrain);

    addCommands(
      new ParallelCommandGroup(
        new FollowTrajectory(Trajectories.SCRIMMAGE_AUTO, drivetrain),
        new WaitCommand(1)
          .andThen(new ToggleArm(arm))
          .andThen(
            new RunIntake(5, intake)
            .alongWith(new RunFeeder(5, feeder))
          )
      ),
      new TurnDegrees(180, drivetrain),
      new LimelightSeek(turret),
      new RunCommand(() -> shooter.setRawSpeed(1), shooter)
    );
  }
}
