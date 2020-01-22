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
    Command test_auto = new FollowTrajectory(Trajectories.TEST_TRAJECTORY, drivetrain),
            blue_auto = new BlueAuto(drivetrain),
            characterization_command = new DrivetrainCharacterization(drivetrain),
            default_command = new InstantCommand();

    addOption("Test Auto", test_auto);
    addOption("Blue Auto", blue_auto);
    addOption("Characterization Routine", characterization_command);
    setDefaultOption("No Autonomous", default_command);
  }
}
