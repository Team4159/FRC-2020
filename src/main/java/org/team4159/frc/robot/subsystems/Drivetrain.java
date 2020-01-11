package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private SpeedControllerGroup left_talons;
  private SpeedControllerGroup right_talons;

  public Drivetrain() {
    left_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_FRONT_TALON_ID)),
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_REAR_TALON_ID)));
    right_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_FRONT_TALON_ID)),
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_REAR_TALON_ID)));
  }

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  public void setRawSpeeds(double left, double right) {
    left_talons.set(left);
    right_talons.set(right);
  }
}