package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;

public class NeckCmd extends CommandBase{
    private Neck neck;

    public NeckCmd(Neck neck) {
        this.neck = neck;
        addRequirements(neck);
    }

    @Override
    public execute() {
        neck.neck();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
