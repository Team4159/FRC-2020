package main.java.org.team4159.frc.robot.commands.subystems;

import edu.wpilib.first.wpilibj2.command.CommandBase;


public class ArmDownCmd extends CommandBase {

    private final Arm arm;
    
    
    public ArmDownCmd(Arm arm) {
        this.arm = arm;
        addRequirements(arm);
    }

    @Override
    public void execute() {
        if(arm.getController().getSetpoint() != ARM_CONSTANTS.DOWN_POSITION) {
            arm.getController.setSetpoint(ARM_CONSTANTS.DOWN_POSITION);
          }
    }

    @Override
    public boolean isFinished(){
        return false;
    }

}
