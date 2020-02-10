package org.team4159.frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.commands.auto.FollowTrajectory;

public class AutoSelector extends SendableChooser<Command> {
  public AutoSelector(Drivetrain drivetrain) {
    Command one = new FollowTrajectory(Trajectories.GO_FORWARD_ONE_METER, drivetrain);

    setDefaultOption("1", one);
  }
}
