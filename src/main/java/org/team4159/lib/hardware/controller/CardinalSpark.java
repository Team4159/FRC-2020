package org.team4159.lib.hardware.controller;

import com.revrobotics.CANSparkMax;

public class CardinalSpark extends CANSparkMax {
  public static final int DEFAULT_CURRENT_LIMIT = 40;

  public CardinalSpark(int id, IdleMode mode) {
    this(id, mode, DEFAULT_CURRENT_LIMIT);
  }

  public CardinalSpark(int id, IdleMode mode, int current_limit) {
    super(id, MotorType.kBrushless);

    super.restoreFactoryDefaults();
    super.setSmartCurrentLimit(current_limit);
    super.setIdleMode(mode);
    super.burnFlash();
  }
}
