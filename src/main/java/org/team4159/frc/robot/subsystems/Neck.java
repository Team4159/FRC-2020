package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.frc.robot.Constants;

public class Neck extends SubsystemBase {
    private SpeedControllerGroup neck_talons;

    public Neck() {
        neck_talons = new SpeedControllerGroup(
          (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(Constants.CAN_IDS.NECK_TALON_ONE_ID)),
          (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(Constants.CAN_IDS.NECK_TALON_TWO_ID)),
          (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(Constants.CAN_IDS.NECK_TALON_THREE_ID))
        );
    }

    private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
        talonSRX.configFactoryDefault();
        talonSRX.setNeutralMode(NeutralMode.Brake);

        return talonSRX;
    }

    public void neck() {
        neck_talons.set(1);
    }

    public void stop() {
        neck_talons.set(0);
    }
}