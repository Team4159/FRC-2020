package org.team4159.frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.team4159.frc.robot.subsystems.Drivetrain;
import org.team4159.frc.robot.subsystems.ControlPanelSpinner;

import static org.team4159.frc.robot.Constants.*;

public class RobotContainer {
  private final Drivetrain drivetrain = new Drivetrain();
  private final ControlPanelSpinner control_panel_spinner = new ControlPanelSpinner();

  private final Joystick left_joy = new Joystick(CONTROLS.LEFT_JOY);
  private final Joystick right_joy = new Joystick(CONTROLS.RIGHT_JOY);

  public RobotContainer() {
    drivetrain.setDefaultCommand(
            new RunCommand(() -> drivetrain.setRawSpeeds(
                    left_joy.getY(),
                    right_joy.getY()
            ), drivetrain));
  }

  public void writeLogOutputs() {
    ControlPanelSpinner.ControlPanelColor color = control_panel_spinner.getColor();
    SmartDashboard.putString("detected_control_panel_color", color.toString());
  }
}
