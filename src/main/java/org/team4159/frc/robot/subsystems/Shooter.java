package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.frc.robot.controllers.ShooterController;
import org.team4159.lib.hardware.EnhancedEncoder;
import org.team4159.lib.hardware.Limelight;
import org.team4159.lib.hardware.controller.ctre.CardinalSPX;
import org.team4159.lib.hardware.controller.ctre.CardinalSRX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends SubsystemBase {
  private CardinalSRX primary_shooter_talon, shooter_talon_two;
  private CardinalSPX shooter_victor_one, shooter_victor_two;

  private EnhancedEncoder shooter_encoder;

  private ShooterController shooter_controller;

  public Shooter() {
    primary_shooter_talon = new CardinalSRX(CAN_IDS.PRIMARY_SHOOTER_TALON, NeutralMode.Coast);
    shooter_talon_two = new CardinalSRX(CAN_IDS.SHOOTER_TALON_TWO, NeutralMode.Coast);
    //shooter_victor_one = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_ONE, NeutralMode.Coast);
    //shooter_victor_two = new CardinalSPX(CAN_IDS.SHOOTER_VICTOR_TWO, NeutralMode.Coast);

    //primary_shooter_talon.configOpenloopRamp(1);

    shooter_talon_two.follow(primary_shooter_talon);
    //shooter_victor_one.follow(primary_shooter_talon);
    //shooter_victor_two.follow(primary_shooter_talon);

    shooter_encoder = new EnhancedEncoder(new Encoder(
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_A_PORT,
      SHOOTER_CONSTANTS.ENCODER_CHANNEL_B_PORT,
      SHOOTER_CONSTANTS.IS_ENCODER_REVERSED,
      SHOOTER_CONSTANTS.ENCODER_ENCODING_TYPE
    ));

    shooter_controller = new ShooterController(this, Limelight.getDefault());
  }

  @Override
  public void periodic() {
    // shooter_controller.update();
  }

  public void setRawSpeed(double speed) {
    primary_shooter_talon.set(speed);
  }

  public void setRawVoltage(double voltage) {
    primary_shooter_talon.setVoltage(voltage);
  }

  public void stop() {
    setRawSpeed(0);
  }

  public double getSpeed() {
    return shooter_encoder.getVelocity();
  }

  public ShooterController getController() {
    return shooter_controller;
  }
}
