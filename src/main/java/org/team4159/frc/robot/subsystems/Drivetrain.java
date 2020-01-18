package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import static org.team4159.frc.robot.Constants.*;

public class Drivetrain extends SubsystemBase {
  private SpeedControllerGroup left_talons;
  private SpeedControllerGroup right_talons;

  private DifferentialDrive drive;

  private boolean is_oriented_forward;

  public Drivetrain() {
    is_oriented_forward = true;

    left_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_FRONT_TALON)),
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.LEFT_REAR_TALON)));
    right_talons = new SpeedControllerGroup(
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_FRONT_TALON)),
            (WPI_TalonSRX) configureTalonSRX(new WPI_TalonSRX(CAN_IDS.RIGHT_REAR_TALON)));

    drive = new DifferentialDrive(left_talons, right_talons);

    right_talons.setInverted(true);
  }

  private TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(NeutralMode.Coast);

    return talonSRX;
  }

  public void setRawSpeeds(double left, double right) {
    if (is_oriented_forward) {
      drive.tankDrive(left, right);
    } else {
      drive.tankDrive(-right, -left);
    }
  }

  public void flipOrientation() {
    is_oriented_forward = !is_oriented_forward;
  }
}