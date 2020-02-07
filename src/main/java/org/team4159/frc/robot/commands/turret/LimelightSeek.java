package org.team4159.frc.robot.commands.turret;

import edu.wpi.first.wpilibj2.command.CommandBase;

import org.team4159.frc.robot.subsystems.Turret;
import org.team4159.lib.hardware.Limelight;

import static org.team4159.frc.robot.Constants.*;


public class LimelightSeek extends CommandBase {
    private Turret turret;
    private Limelight limelight;
    private double prevError = 0;

    public LimelightSeek(Turret turret, Limelight limelight) {
        this.turret = turret;
        this.limelight = limelight;

        addRequirements(turret);
    }

    @Override
    public void initialize() {
        limelight.setLEDMode(Limelight.LEDMode.ForceOn);
    }

    @Override
    public void execute() {
        if (limelight.isTargetVisible()) {
            double error = limelight.getTargetHorizontalOffset();
            double speed = error* TURRET_CONSTANTS.LIMELIGHT_kP + (error-prevError)*TURRET_CONSTANTS.LIMELIGHT_kD;
            prevError = error;
            turret.setRawSpeed(speed);
        }
    }

    @Override
    public void end(boolean interrupted) {
        turret.stop();
        limelight.setLEDMode(Limelight.LEDMode.ForceOff);
    }
}
