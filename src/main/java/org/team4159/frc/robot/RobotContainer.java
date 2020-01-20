package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.RunCommand;

import org.team4159.frc.robot.commands.turret.ZeroTurret;
import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.Turret;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final Turret turret = new Turret();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

  public RobotContainer() {
    drivetrain.setDefaultCommand(
      new RunCommand(() -> drivetrain.setRawSpeeds(
        left_joy.getY(),
        right_joy.getY()
      ), drivetrain));

    new ZeroTurret(turret).schedule();
  }
}
