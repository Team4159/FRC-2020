package org.team4159.lib.hardware.controller.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class CardinalSRX extends WPI_TalonSRX {
  private double last_setpoint;
  private ControlMode last_control_mode;

  public CardinalSRX(int id, NeutralMode mode) {
    super(id);

    super.configFactoryDefault();
    super.setNeutralMode(mode);

    last_setpoint = Double.NaN;
    last_control_mode = null;
  }

  @Override
  public void set(ControlMode control_mode, double setpoint) {
    if (setpoint != last_setpoint || control_mode != last_control_mode) {
      super.set(control_mode, setpoint);

      last_setpoint = setpoint;
      last_control_mode = control_mode;
    }
  }
}
