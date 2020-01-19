package org.team4159.frc.robot.commands.auto;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.subsystems.Drivetrain;

import static org.team4159.frc.robot.Constants.*;

public class FollowTrajectory extends SequentialCommandGroup {
  public FollowTrajectory(Trajectory trajectory, Drivetrain drivetrain) {
    addRequirements(drivetrain);

    drivetrain.zeroSensors();

    addCommands(
            new RamseteCommand(
                    trajectory, // desired trajectory to follow
                    drivetrain::getPose, // method reference to pose supplier
                    new RamseteController(DRIVE_CONSTANTS.kB,
                            DRIVE_CONSTANTS.kZeta), // object that performs path-following computation
                    new SimpleMotorFeedforward(DRIVE_CONSTANTS.kS,
                            DRIVE_CONSTANTS.kV,
                            DRIVE_CONSTANTS.kA), // feedforward gains kS, kV, kA obtained from characterization
                    new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH), // track width
                    drivetrain::getWheelSpeeds, // method reference to wheel speed supplier
                    new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD), // left side PID using Proportional gain from characterization
                    new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD), // right side PID using Proportional gain from characterization
                    drivetrain::voltsDrive, // method reference to pass voltage outputs to motors
                    drivetrain // require drivetrain subsystem
            ),
            new InstantCommand(
                    () -> drivetrain.voltsDrive(0.0, 0.0) // stop after path finished
            ),
            new PrintCommand(
                    "Trajectory Finished!"
            )
    );
  }
}
