package org.team4159.frc.robot.commands.subsystems;

import org.team4159.frc.robot.Constants.SHOOTER_CONSTANTS;
import org.team4159.frc.robot.subsystems.Neck;
import org.team4159.frc.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ShootCmd extends CommandBase{
    private Shooter shooter;
    private Neck neck;
    private Timer timer = new Timer();
    
    public ShootCmd(Shooter shooter, Neck neck){
        this.shooter = shooter;
        this.neck = neck;
        addRequirements(shooter, neck);
        timer.start();
    }
   
    
    @Override
    public void execute() {
        //Don't look at this it pains me too its temporary
        while(timer.get() < 5d){
            neck.setRawSpeed(0.4d);
            shooter.setRawSpeed(10.0d);

        }
    }
    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        return timer.get() == 5d;
    }
}
