package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.frc.robot.Constants;

public class Intake extends SubsystemBase {
    private TalonSRX intakeTalon;

    private Intake() {
        intakeTalon = new TalonSRX(Constants.CAN_IDS.INTAKE_TALON);
        intakeTalon.configFactoryDefault();
        intakeTalon.setNeutralMode(NeutralMode.Brake);
    }

    public void in() {
        intakeTalon.set(ControlMode.PercentOutput, 1.0);
    }

    public void out() {
        intakeTalon.set(ControlMode.PercentOutput, -1.0);
    }

    public void stop() {
        intakeTalon.set(ControlMode.PercentOutput, 0);
    }

}
