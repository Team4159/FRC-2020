package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.*;

import org.team4159.frc.robot.commands.arm.ToggleArm;
import org.team4159.frc.robot.subsystems.*;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Shooter shooter = new Shooter();
  private final Intake intake = new Intake();
  private final Feeder feeder = new Feeder();
  private final Neck neck = new Neck();
  public final Arm arm = new Arm();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY.PORT);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY.PORT);
  private final Joystick secondary_joy = new Joystick(CONTROLS.SECONDARY_JOY.PORT);

  private final AutoSelector auto_selector = new AutoSelector(drivetrain);

  public RobotContainer() {
    drivetrain.setDefaultCommand(new RunCommand(
      () -> drivetrain.tankDrive(
        left_joy.getY(),
        right_joy.getY()
      ),
      drivetrain
    ));

    //arm.setDefaultCommand(new RunCommand(() -> arm.setRawSpeed(secondary_joy.getY()), arm));

    configureButtonBindings();
  }

  private void configureButtonBindings() {
    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_SHOOTER_BTN)
      .whenPressed(new ConditionalCommand(
        new InstantCommand(shooter::enable, shooter),
        new InstantCommand(shooter::disable, shooter),
        shooter::isEnabled
      ));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.FLIP_ORIENTATION_BTN_ID)
      .whenPressed(drivetrain::flipOrientation);

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_INTAKE_BTN_ID)
      .whenPressed(new InstantCommand(intake::intakeCell, intake))
      .whenReleased(new InstantCommand(intake::stopIntaking, intake));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.TOGGLE_ARM_BTN_ID)
      .whenPressed(new ToggleArm(arm));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_NECK_BTN_ID)
      .whenPressed(new InstantCommand(neck::feedCell))
      .whenReleased(new InstantCommand(neck::stop));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_FEEDER_BTN_ID)
      .whenPressed(new InstantCommand(feeder::feedCell, feeder))
      .whenReleased(new InstantCommand(feeder::stop, feeder));

    new JoystickButton(secondary_joy, CONTROLS.SECONDARY_JOY.RUN_INTAKE_NECK_FEEDER_SHOOTER_BTN_ID)
      .whenPressed(new ParallelCommandGroup(
        new InstantCommand(intake::intakeCell, intake),
        new InstantCommand(feeder::feedCell, feeder),
        new InstantCommand(neck::feedCell),
        new InstantCommand(() -> shooter.setRawSpeed(1))))
      .whenReleased(new ParallelCommandGroup(
        new InstantCommand(intake::stopIntaking, intake),
        new InstantCommand(feeder::stop, feeder),
        new InstantCommand(neck::stop),
        new InstantCommand(() -> shooter.setRawSpeed(0))));
  }


  public Command getAutonomousCommand() {
    return auto_selector.getSelected();
  }
}
