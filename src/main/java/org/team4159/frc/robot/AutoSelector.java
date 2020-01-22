package org.team4159.frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.commands.auto.BlueAuto;
import org.team4159.frc.robot.commands.auto.FollowTrajectory;
import org.team4159.frc.robot.commands.characterization.DrivetrainCharacterization;

public class AutoSelector extends SendableChooser<Command> {
  public AutoSelector(Drivetrain drivetrain) {
    Command one = new FollowTrajectory(Trajectories.GO_FORWARD_ONE_METER, drivetrain),
            two = new FollowTrajectory(Trajectories.TURN_RIGHT_90_GO_FORWARD_ONE_METER, drivetrain),
            three = new FollowTrajectory(Trajectories.TEST_3, drivetrain);


    addOption("1", one);
    addOption("2", two);
    setDefaultOption("3", three);
  }
}
