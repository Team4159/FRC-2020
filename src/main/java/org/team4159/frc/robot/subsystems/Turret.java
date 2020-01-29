package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends SubsystemBase {
    private TalonFX turret_talon;

    private TalonFX configureTalonFX(TalonFX talonFX) {
        talonFX.configFactoryDefault();
        talonFX.setNeutralMode(NeutralMode.Coast);

        return talonFX;
    }

    public Turret(){
        turret_talon = configureTalonFX(new WPI_TalonFX(CAN_IDS.TURRET_FALCON_ID));
    }

    public void setRawSpeed(double speed){
        turret_talon.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        setRawSpeed(0);
    }
}
