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

    addCommands(
      new RamseteCommand(
        trajectory,
        drivetrain::getPose,
        new RamseteController(DRIVE_CONSTANTS.kB,
                              DRIVE_CONSTANTS.kZeta),
        new SimpleMotorFeedforward(DRIVE_CONSTANTS.kS,
                                   DRIVE_CONSTANTS.kV,
                                   DRIVE_CONSTANTS.kA),
        new DifferentialDriveKinematics(DRIVE_CONSTANTS.TRACK_WIDTH),
        drivetrain::getWheelSpeeds,
        new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD),
        new PIDController(DRIVE_CONSTANTS.kP, 0, DRIVE_CONSTANTS.kD),
        drivetrain::voltsDrive,
        drivetrain
      ),
      new InstantCommand(drivetrain::stop)
    );
  }
}
