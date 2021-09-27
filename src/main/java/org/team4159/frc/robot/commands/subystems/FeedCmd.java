package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;


public class FeedCmd {
    
    private Feeder feeder;

    public FeedCmd(Feeder feeder) {
        this.feeder = feeder;
        addRequirements(feeder);
    }

    @Override
    public execute() {
        feeder.feed();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
