package org.team4159.frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import org.team4159.frc.robot.Constants;
import org.team4159.frc.robot.Trajectories;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.lib.control.signal.DriveSignal;

import java.util.function.BiConsumer;

public class FollowTrajectory extends RamseteCommand {

    public FollowTrajectory(Trajectory trajectory, Drivetrain drivetrain) {
        super(
            trajectory,
            drivetrain::getPose,
            new RamseteController(Constants.DRIVE_CONSTANTS.kB, Constants.DRIVE_CONSTANTS.kZeta),
            new SimpleMotorFeedforward(Constants.DRIVE_CONSTANTS.kS, Constants.DRIVE_CONSTANTS.kV, Constants.DRIVE_CONSTANTS.kA),
            new DifferentialDriveKinematics(Constants.DRIVE_CONSTANTS.TRACK_WIDTH),
            drivetrain::getWheelSpeeds,
            new PIDController(Constants.DRIVE_CONSTANTS.kP, Constants.DRIVE_CONSTANTS.kI, Constants.DRIVE_CONSTANTS.kD),
            new PIDController(Constants.DRIVE_CONSTANTS.kP, Constants.DRIVE_CONSTANTS.kI, Constants.DRIVE_CONSTANTS.kD),
            (leftVolts, rightVolts) -> drivetrain.drive(DriveSignal.fromVolts(leftVolts, rightVolts)),
            drivetrain
            );
    }
}
