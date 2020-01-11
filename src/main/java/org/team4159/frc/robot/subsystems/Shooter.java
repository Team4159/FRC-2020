package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.SensorCollection;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj.SpeedControllerGroup;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Shooter extends PIDSubsystem {
  private SpeedControllerGroup shooter_motors;
  private SensorCollection shooter_encoder;

  public Shooter() {
    super(new PIDController(SHOOTER_CONSTANTS.kP, SHOOTER_CONSTANTS.kI, SHOOTER_CONSTANTS.kD));

    TalonSRX primary_shooter_talon, secondary_shooter_talon;

    primary_shooter_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.PRIMARY_SHOOTER_TALON_ID));
    secondary_shooter_talon = configureTalonSRX(new WPI_TalonSRX(CAN_IDS.SECONDARY_SHOOTER_TALON_ID));

    shooter_encoder = primary_shooter_talon.getSensorCollection();

    shooter_motors = new SpeedControllerGroup(
      (WPI_TalonSRX) primary_shooter_talon,
      (WPI_TalonSRX) secondary_shooter_talon
    );
  }

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  public void setRawSpeed(double speed) {
    shooter_motors.set(speed);
  }

  public double getVelocity() {
    return shooter_encoder.getQuadratureVelocity();
  }

  @Override
  protected void useOutput(double output, double setpoint) {
    setRawSpeed(output);
  }

  @Override
  protected double getMeasurement() {
    return getVelocity();
  }
}
