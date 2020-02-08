package org.team4159.lib.hardware.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;

public class ControllerUtil {
  public static final int kSparkMaxDefaultCurrentLimit = 40;

  public static TalonFX configureTalonFX(TalonFX talonFX) {
    return configureTalonFX(talonFX, NeutralMode.Coast);
  }

  public static TalonFX configureTalonFX(TalonFX talonFX, NeutralMode mode) {
    talonFX.configFactoryDefault();
    talonFX.setNeutralMode(mode);

    return talonFX;
  }

  public static TalonSRX configureTalonSRX(TalonSRX talonSRX) {
    return configureTalonSRX(talonSRX, NeutralMode.Coast);
  }

  public static TalonSRX configureTalonSRX(TalonSRX talonSRX, NeutralMode mode) {
    talonSRX.configFactoryDefault();
    talonSRX.setNeutralMode(mode);

    return talonSRX;
  }

  public static VictorSPX configureVictorSPX(VictorSPX victorSPX) {
    return configureVictorSPX(victorSPX, NeutralMode.Coast);
  }

  public static VictorSPX configureVictorSPX(VictorSPX victorSPX, NeutralMode mode) {
    victorSPX.configFactoryDefault();
    victorSPX.setNeutralMode(mode);

    return victorSPX;
  }

  public static CANSparkMax configureSparkMax(CANSparkMax spark) {
    return configureSparkMax(spark, CANSparkMax.IdleMode.kCoast, kSparkMaxDefaultCurrentLimit);
  }

  public static CANSparkMax configureSparkMax(CANSparkMax spark, CANSparkMax.IdleMode mode) {
    return configureSparkMax(spark, mode, kSparkMaxDefaultCurrentLimit);
  }

  public static CANSparkMax configureSparkMax(CANSparkMax spark, CANSparkMax.IdleMode mode, int current_limit) {
    spark.restoreFactoryDefaults();
    spark.setSmartCurrentLimit(current_limit);
    spark.setIdleMode(mode);
    spark.burnFlash();

    return spark;
  }
}
