package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends PIDSubsystem {
    private TalonFX turret_talon;
    private Limelight limelight;

    private TalonFX configureTalonFX(TalonFX talonFX) {
        talonFX.configFactoryDefault();
        talonFX.setNeutralMode(NeutralMode.Coast);

        return talonFX;
    }

    public Turret() {
        super(new PIDController(
          TURRET_CONSTANTS.LIMELIGHT_TURN_kP,
          0.0,
          TURRET_CONSTANTS.LIMELIGHT_TURN_kD
        ));

        limelight = new Limelight();
        turret_talon = configureTalonFX(new WPI_TalonFX(CAN_IDS.TURRET_FALCON_ID));
    }

    public void setRawSpeed(double speed){
        turret_talon.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        setRawSpeed(0);
    }

    @Override
    protected double getMeasurement() {
        return limelight.getTargetHorizontalOffset();
    }

    @Override
    protected void useOutput(double output, double setpoint) {
        setRawSpeed(output);
    }

    @Override
    public void periodic() {
        double vertical_offset = limelight.getTargetVerticalOffset();
        double distance = (LIMELIGHT_CONSTANTS.VISION_TARGET_HEIGHT - LIMELIGHT_CONSTANTS.MOUNT_ELEVATION) /
                          Math.tan(Math.toRadians(LIMELIGHT_CONSTANTS.MOUNT_ANGLE + vertical_offset));
        SmartDashboard.putNumber("distance in inches", distance);
        SmartDashboard.putNumber("distance in feet", distance / 12);
        SmartDashboard.putNumber("vertical offset", vertical_offset);
    }
}
