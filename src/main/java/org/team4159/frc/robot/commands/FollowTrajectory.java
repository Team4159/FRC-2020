package org.team4159.frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.*;
import org.team4159.frc.robot.Constants;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class FollowTrajectory extends SequentialCommandGroup {
  public FollowTrajectory(Trajectory trajectory,
                          Subsystem subsystem,
                          Supplier<Pose2d> pose_supplier,
                          Supplier<DifferentialDriveWheelSpeeds> wheel_speeds,
                          BiConsumer<Double, Double> output_voltages) {
    addCommands(
            new RamseteCommand(
                    trajectory, // desired trajectory to follow
                    pose_supplier, // method reference to pose supplier
                    new RamseteController(Constants.DRIVE_CONSTANTS.kB,
                            Constants.DRIVE_CONSTANTS.kZeta), // object that performs path-following computation
                    new SimpleMotorFeedforward(Constants.DRIVE_CONSTANTS.kS,
                            Constants.DRIVE_CONSTANTS.kV,
                            Constants.DRIVE_CONSTANTS.kA), // feedforward gains kS, kV, kA obtained from characterization
                    new DifferentialDriveKinematics(Constants.DRIVE_CONSTANTS.TRACK_WIDTH), // track width
                    wheel_speeds, // method reference to wheel speed supplier
                    new PIDController(Constants.DRIVE_CONSTANTS.kP, 0, 0), // left side PID using Proportional gain from characterization
                    new PIDController(Constants.DRIVE_CONSTANTS.kP, 0, 0), // right side PID using Proportional gain from characterization
                    output_voltages, // method reference to pass voltage outputs to motors
                    subsystem // require drivetrain subsystem
            ),
            new RunCommand(
                    () -> output_voltages.accept(0.0, 0.0) // stop after path finished
            )
    );
  }
}
