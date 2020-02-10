package org.team4159.lib.hardware.controller.rev;

import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

public class CardinalMAX extends CANSparkMax {
  public static final int DEFAULT_CURRENT_LIMIT = 40;

  private double last_setpoint;
  private ControlType last_control_type;

  public CardinalMAX(int id, IdleMode mode) {
    this(id, mode, DEFAULT_CURRENT_LIMIT, MotorType.kBrushless);
  }

  public CardinalMAX(int id, IdleMode mode, int current_limit, MotorType type) {
    super(id, type);

    super.restoreFactoryDefaults();
    super.setSmartCurrentLimit(current_limit);
    super.setIdleMode(mode);
    super.burnFlash();
  }

  @Override
  public void set(double setpoint) {
    this.set(ControlType.kDutyCycle, setpoint);
  }

  public void set(ControlType control_type, double setpoint) {
    if (setpoint != last_setpoint || control_type != last_control_type) {
      if (control_type == ControlType.kDutyCycle) {
        super.set(setpoint);
      } else {
        super.getPIDController().setReference(setpoint, control_type);
      }

      last_setpoint = setpoint;
      last_control_type = control_type;
    }
  }
}
