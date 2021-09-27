package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;


public class RunIntakeCmd extends CommandBase{
    private Feeder intake;

    public RunIntakeCmd(Intake intake) {
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public execute() {
        intake.intake();
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
