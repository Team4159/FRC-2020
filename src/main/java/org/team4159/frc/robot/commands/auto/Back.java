package org.team4159.frc.robot.commands.auto;

import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.control.signal.DriveSignal;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Back extends CommandBase{
    private Drivetrain dt;
    private PIDController controller;
    public Back(Drivetrain dt){
        this.dt = dt;
        controller = new PIDController(Constants.DRIVE_CONSTANTS.kP,0,0);
        controller.setSetpoint(5d);
        addRequirements(dt);
    }
    @Override
    public void execute() {
        double error = controller.calculate( ((dt.getRightDistance() - 5)+(dt.getLeftDistance()-5))/2 ); //"Something is wrong I can feel it"
        dt.drive(new DriveSignal(error, error));
    }
    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }
}
