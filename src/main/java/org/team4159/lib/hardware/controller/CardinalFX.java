package org.team4159.lib.hardware.controller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class CardinalFX extends TalonFX {

  public CardinalFX(int id, NeutralMode mode) {
    super(id);

    super.configFactoryDefault();
    super.setNeutralMode(mode);
  }
}
