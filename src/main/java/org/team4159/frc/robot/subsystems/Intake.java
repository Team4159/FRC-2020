package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    private TalonSRX arm_talon, intake_talon;

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Brake);

        return talonSRX;
    }

    private Intake() {
        arm_talon = configureTalonSRX(new TalonSRX(CAN_IDS.ARM_TALON));
        arm_talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        intake_talon = configureTalonSRX(new TalonSRX(CAN_IDS.INTAKE_TALON));
    }

    public void raiseArm() {
        arm_talon.set(ControlMode.PercentOutput, 1);
    }

    public void lowerArm() {
        arm_talon.set(ControlMode.PercentOutput, -1);
    }

    public void intakeCell() {
        intake_talon.set(ControlMode.PercentOutput, 1);
    }

    public void stopIntaking() {
        intake_talon.set(ControlMode.PercentOutput, 0);
    }
}
