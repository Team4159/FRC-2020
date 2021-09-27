package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;

public class ArmUpCmd extends CommandBase{
    private final Arm arm;
    
    
    public ArmDownCmd(Arm arm) {
        this.arm = arm;
        addRequirements(arm); //Tells Command that it uses Arm subsystem
    }

    @Override
    public void execute() {
        if(arm.getController().getSetpoint() != ARM_CONSTANTS.UP) {
            arm.getController.setSetpoint(ARM_CONSTANTS.UP);
          }
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
