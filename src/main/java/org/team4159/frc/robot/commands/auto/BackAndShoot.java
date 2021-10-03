package org.team4159.frc.robot.commands.auto;

import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.commands.subsystems.ShootCmd;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.Neck;
import org.team4159.frc.robot.subsystems.Shooter;
import org.team4159.lib.control.signal.DriveSignal;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj.controller.PIDController;

public class BackAndShoot extends SequentialCommandGroup {
    private Drivetrain dt;
    private Shooter shooter;
    private Neck neck;

    public BackAndShoot(Drivetrain dt, Shooter shooter, Neck neck){
        this.dt = dt;
        this.shooter = shooter;
        this.neck = neck;
        addCommands(
            new Back(dt, 3, 0.25),
            new ShootCmd(shooter, neck)
        );
    }
}
