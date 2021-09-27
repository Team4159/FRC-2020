package main.java.org.team4159.frc.robot.commands.subystems;



public class ArmUpCmd extends CommandBase{
    private final Arm arm;
    
    
    public ArmDownCmd(Arm arm) {
        this.arm = arm;
    }

    @Override
    public void execute() {
        if(arm.getController().getSetpoint() != ARM_CONSTANTS.UP) {
            arm.getController.setSetpoint(ARM_CONSTANTS.UP);
          }
    }
}
