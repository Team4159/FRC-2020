package org.team4159.lib.hardware.controller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class CardinalSRX extends TalonSRX {

  public CardinalSRX(int id, NeutralMode mode) {
    super(id);

    super.configFactoryDefault();
    super.setNeutralMode(mode);
  }
}
