package org.team4159.frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

import com.revrobotics.ColorMatch;
import com.revrobotics.ColorSensorV3;
import com.revrobotics.ColorMatchResult;

import static org.team4159.frc.robot.Constants.*;

public class ControlPanelSpinner {
  public enum ControlPanelColor {
    BLUE,
    GREEN,
    RED,
    YELLOW,
    NONE
  }

  private ColorSensorV3 color_sensor;
  private ColorMatch color_match;

  public ControlPanelSpinner() {
    color_sensor = new ColorSensorV3(I2C.Port.kOnboard);

    color_match = new ColorMatch();
    color_match.addColorMatch(FIELD_CONSTANTS.CONTROL_SPINNER_BLUE);
    color_match.addColorMatch(FIELD_CONSTANTS.CONTROL_SPINNER_GREEN);
    color_match.addColorMatch(FIELD_CONSTANTS.CONTROL_SPINNER_RED);
    color_match.addColorMatch(FIELD_CONSTANTS.CONTROL_SPINNER_YELLOW);
  }

  public ControlPanelColor getColor() {
    Color detected_color = color_sensor.getColor();
    ColorMatchResult color_match_result = color_match.matchClosestColor(detected_color);

    if (color_match_result.color == FIELD_CONSTANTS.CONTROL_SPINNER_BLUE) {
      return ControlPanelColor.BLUE;
    } else if (color_match_result.color == FIELD_CONSTANTS.CONTROL_SPINNER_GREEN) {
      return ControlPanelColor.GREEN;
    } else if (color_match_result.color == FIELD_CONSTANTS.CONTROL_SPINNER_RED) {
      return ControlPanelColor.RED;
    } else if (color_match_result.color == FIELD_CONSTANTS.CONTROL_SPINNER_YELLOW) {
      return ControlPanelColor.YELLOW;
    } else {
      return ControlPanelColor.NONE;
    }
  }
}
