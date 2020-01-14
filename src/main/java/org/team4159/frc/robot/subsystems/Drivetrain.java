package org.team4159.frc.robot.subsystems;

import badlog.lib.BadLog;
import badlog.lib.DataInferMode;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends LoggableSubsystem {
  private SpeedControllerGroup left_talons;
  private SpeedControllerGroup right_talons;

  public Drivetrain() {
    left_talons = new SpeedControllerGroup(
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_FRONT_TALON)),
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_REAR_TALON)));
    right_talons = new SpeedControllerGroup(
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_FRONT_TALON)),
      (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_REAR_TALON)));
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

  @Override
  public void loggingInit() {
    BadLog.createTopic(getName() + "/Right Output Percent", BadLog.UNITLESS,
      right_talons::get, "join:Output Percents");
    BadLog.createTopic(getName() + "/Left Output Percent", BadLog.UNITLESS,
      left_talons::get, "join:Output Percents");
  }
}