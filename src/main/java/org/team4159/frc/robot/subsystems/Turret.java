package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.team4159.lib.hardware.Limelight;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import static org.team4159.frc.robot.Constants.*;

public class Turret extends PIDSubsystem {
    private TalonFX turret_talon;
    private Limelight limelight;
    double limelightHeight,targetHeight,limeLightMountingAngle;

    private TalonFX configureTalonFX(TalonFX talonFX) {
        talonFX.configFactoryDefault();
        talonFX.setNeutralMode(NeutralMode.Coast);

        return talonFX;
    }

    public Turret() {
        super(new PIDController(
          TURRET_CONSTANTS.LIMELIGHT_kP,
          0.0,
          TURRET_CONSTANTS.LIMELIGHT_kD
        ));

        limelight = new Limelight();
        turret_talon = configureTalonFX(new WPI_TalonFX(CAN_IDS.TURRET_FALCON_ID));
        limelightHeight = 9;
        targetHeight = 51.5;
        limeLightMountingAngle = 25;
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
        double yOffset = limelight.getTargetVerticalOffset();
        double distance = (targetHeight- limelightHeight)/Math.tan(Math.toRadians(limeLightMountingAngle+yOffset));
        SmartDashboard.putNumber("distance-inches",distance);
        SmartDashboard.putNumber("distance-feet",distance/12.0);
        SmartDashboard.putNumber("yOffset",yOffset);
    }
}
