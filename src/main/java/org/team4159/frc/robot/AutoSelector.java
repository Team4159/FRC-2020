package org.team4159.frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.commands.auto.BlueAuto;

public class AutoSelector extends SendableChooser<Command> {
  public AutoSelector(Drivetrain drivetrain) {
    Command blue_auto = new BlueAuto(drivetrain),
            default_command = new InstantCommand();

    addOption("Blue Auto", blue_auto);
    setDefaultOption("No Autonomous", default_command);
  }
}
