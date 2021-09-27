package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;

public class LimelightTrackCmd {
    private Limelight limelight;

    public LimelightTrackCmd(Limelight limelight) {
        this.limelight = limelight;
    }

    @Override
    public execute() {
        turret.getController().startSeeking();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
