package org.team4159.lib.hardware.controller;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class CardinalSPX extends VictorSPX {

  public CardinalSPX(int id, NeutralMode mode) {
    super(id);

    super.configFactoryDefault();
    super.setNeutralMode(mode);
  }
}
