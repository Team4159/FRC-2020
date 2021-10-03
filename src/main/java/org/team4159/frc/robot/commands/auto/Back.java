package org.team4159.frc.robot.commands.auto;

import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.control.signal.DriveSignal;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Back extends CommandBase{
    private Drivetrain dt;
    private PIDController controller = new PIDController(Constants.DRIVE_CONSTANTS.kP,0,0);
    private double speed;
    private double setpoint;
    private int timer = 0;
    public Back(Drivetrain dt, double distance, double speed){
        this.dt = dt;
        this.setpoint = distance;
        this.speed = speed;
        dt.resetEncoders();
        controller.setSetpoint(distance);
        //controller.setTolerance(5, 10);
        addRequirements(dt);
    }
    @Override
    public void execute() {
        timer ++;
        //double speed = controller.calculate(dt.getLeftDistance(), setpoint);
        System.out.println(speed);
        if( timer < 90 )
          dt.drive(new DriveSignal(speed, speed));
    }
    @Override
    public boolean isFinished() {
        return controller.atSetpoint();
    }
}
