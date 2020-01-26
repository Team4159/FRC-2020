package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Intake extends SubsystemBase {
    private TalonSRX intake_talon;

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Brake);

        return talonSRX;
    }

    public Intake() {
        intake_talon = configureTalonSRX(new TalonSRX(CAN_IDS.INTAKE_TALON_ID));
    }

    public void setRawIntakeSpeed(double speed) {
        intake_talon.set(ControlMode.PercentOutput, speed);
    }

    public void intakeCell() {
        setRawIntakeSpeed(1);
    }

    public void stopIntaking() {
        setRawIntakeSpeed(0);
    }
}
