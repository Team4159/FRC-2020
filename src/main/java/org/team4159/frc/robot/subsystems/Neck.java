package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.team4159.lib.hardware.util.ControllerUtil;

import static org.team4159.frc.robot.Constants.*;

public class Neck extends SubsystemBase {
  private SpeedControllerGroup neck_talons;

  public Neck() {
    neck_talons = new SpeedControllerGroup(
      (WPI_TalonSRX) ControllerUtil.configureTalonSRX(new WPI_TalonSRX(CAN_IDS.NECK_TALON_ONE_ID), NeutralMode.Brake),
      (WPI_TalonSRX) ControllerUtil.configureTalonSRX(new WPI_TalonSRX(CAN_IDS.NECK_TALON_TWO_ID), NeutralMode.Brake),
      (WPI_TalonSRX) ControllerUtil.configureTalonSRX(new WPI_TalonSRX(CAN_IDS.NECK_TALON_THREE_ID), NeutralMode.Brake)
    );
  }

  public void feedCell() {
    neck_talons.set(1);
  }

  public void stop() {
    neck_talons.set(0);
  }
}