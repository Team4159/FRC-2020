package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.sensors.PigeonIMU;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import org.team4159.lib.control.signal.DriveSignal;
import org.team4159.lib.control.signal.filters.LowPassFilterSource;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private TalonFX left_front_falcon, left_rear_falcon, right_front_falcon, right_rear_falcon;
  private SpeedControllerGroup left_falcons;
  private SpeedControllerGroup right_falcons;

  private PigeonIMU pigeon;
  private LowPassFilterSource filtered_heading;

  private boolean is_oriented_forward = true;

  private TalonFX configureTalonFX(TalonFX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  public Drivetrain() {
    left_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_FRONT_FALCON_ID));
    left_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.LEFT_REAR_FALCON_ID));
    right_front_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_FRONT_FALCON_ID));
    right_rear_falcon = configureTalonFX(new WPI_TalonFX(CAN_IDS.RIGHT_REAR_TALON_ID));

    left_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    right_front_falcon.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    // setSensorPhase isn't working

    left_falcons = new SpeedControllerGroup(
      (WPI_TalonFX) left_front_falcon,
      (WPI_TalonFX) left_rear_falcon);
    right_falcons = new SpeedControllerGroup(
      (WPI_TalonFX) right_front_falcon,
      (WPI_TalonFX) right_rear_falcon);
    left_falcons.setInverted(true);
    right_falcons.setInverted(true);

    pigeon = new PigeonIMU(CAN_IDS.PIGEON_ID);
    filtered_heading = new LowPassFilterSource(pigeon::getFusedHeading, 10);

    zeroSensors();
  }

  @Override
  public void periodic() {
    filtered_heading.get();
  }

  public void rawDrive(DriveSignal signal) {
    if (is_oriented_forward) {
      signal.invert();
    }

    left_falcons.set(signal.left);
    right_falcons.set(signal.right);
  }

  public void tankDrive(double left, double right) {
    rawDrive(new DriveSignal(left, right, true));
  }

  public void arcadeDrive(double speed, double turn) {
    rawDrive(DriveSignal.fromArcade(speed, turn, true));
  }

  public void voltsDrive(double left_volts, double right_volts) {
    rawDrive(DriveSignal.fromVolts(left_volts, right_volts));
  }

  public void stop() {
    rawDrive(DriveSignal.NEUTRAL);
  }

  public void flipOrientation() {
    is_oriented_forward = !is_oriented_forward;
  }

  public void resetEncoders() {
    left_front_falcon.setSelectedSensorPosition(0);
    right_front_falcon.setSelectedSensorPosition(0);
  }

  public void resetDirection() {
    pigeon.setFusedHeading(0);
  }

  public void zeroSensors() {
    resetEncoders();
    resetDirection();
  }
}
