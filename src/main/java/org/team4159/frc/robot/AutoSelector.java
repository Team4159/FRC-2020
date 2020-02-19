package org.team4159.frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import org.team4159.frc.robot.subsystems.Drivetrain;

public class AutoSelector extends SendableChooser<Command> {
  public AutoSelector(Drivetrain drivetrain) {
    Command one = new InstantCommand();

    setDefaultOption("1", one);
  }
}
