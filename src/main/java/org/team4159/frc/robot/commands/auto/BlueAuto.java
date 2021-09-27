package org.team4159.frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import org.team4159.frc.robot.subsystems.*;

/*
 * Placeholder until we plan out an autonomous routine
 */


//REMEMBER TO IMPORT TIMER

public class BlueAuto extends SequentialCommandGroup {
  private final double SHOOTER_TPS = 10000;

  public BlueAuto(Drivetrain drivetrain, Arm arm, Feeder feeder, Intake intake, Turret turret, Shooter shooter, Neck neck) {
    new SequentialCommandGroup (
      new ArmDownCMD(arm),

      new ParallelCommandGroup (

      )//Anything we wanna run at the same time

    );





//addCommands(
//        new SequentialCommandGroup(
//          new ParallelCommandGroup(
//            new FollowTrajectory(Trajectories.SCRIMMAGE_AUTO, drivetrain),
//            new WaitCommand(1)
//              .andThen(new ToggleArm(arm))
//              .andThen(
//                new RunIntake(5, intake)
//                  .alongWith(new RunFeeder(5, feeder))
//              )
//          ),
//          new ParallelCommandGroup(
//            new Shoot(SHOOTER_TPS, shooter, neck, turret),
//            new LimelightSeek(turret)
//          )
//        )
//      );
  }
}
