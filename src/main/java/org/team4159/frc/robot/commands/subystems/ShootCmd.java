package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;

public class ShootCmd extends CommandBase {
    private Shooter shooter;

    public ShootCmd(Shoot shooter) {
        this.shooter = shooter;
        addRequirements(Shooter);
    }

    @Override
    public execute() {
        shooter.getController().setTargetSpeed(SHOOTER_CONSTANTS.MAX_SPEED);
        shooter.getController().spinUp();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
