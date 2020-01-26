package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.team4159.frc.robot.Constants;

public class Intake extends SubsystemBase {
    private TalonSRX intake_talon;

    private Intake() {
        intake_talon = configureTalonSRX(new TalonSRX((Constants.CAN_IDS.INTAKE_TALON)));
    }

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Brake);

        return talonSRX;
    }

    public void intakeCell() {
        intake_talon.set(ControlMode.PercentOutput, 1);
    }

    public void stop() {
        intake_talon.set(ControlMode.PercentOutput, 0);
    }
}
