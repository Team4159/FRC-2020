package org.team4159.lib.simulation.motors;

import org.team4159.lib.physics.DCMotorModel;

public class Motors {
  public static final double TEST_VOLTAGE = 12;

  // From https://motors.vex.com/
  public static final DCMotorModel Falcon_500 = new DCMotorModel("Falcon 500", 6380, 1.5, 783, 4.69, 257, TEST_VOLTAGE);
  public static final DCMotorModel NEO = new DCMotorModel("NEO", 5880, 1.3, 516, 3.36, 166, TEST_VOLTAGE);
  public static final DCMotorModel CIM = new DCMotorModel("CIM", 5330, 2.7, 337, 2.41, 131, TEST_VOLTAGE);
  public static final DCMotorModel MiniCIM = new DCMotorModel("Mini CIM",  5840, 3, 215, 1.41, 89, TEST_VOLTAGE);
  public static final DCMotorModel BAG = new DCMotorModel("BAG", 13180, 1.8, 149, 0.43, 53, TEST_VOLTAGE);
  public static final DCMotorModel _775pro = new DCMotorModel("775pro", 18730, 0.7, 347, 0.71, 134, TEST_VOLTAGE);
  public static final DCMotorModel AndyMark_9015 = new DCMotorModel("AndyMark 9015", 14270, 3.7, 134, 0.36, 71, TEST_VOLTAGE);
  public static final DCMotorModel AndyMark_NeveRest = new DCMotorModel("AndyMark NeveRest", 5480, 0.4, 25, 0.17, 10, TEST_VOLTAGE);
  public static final DCMotorModel AndyMark_RS775_125 = new DCMotorModel("AndyMark RS775-125", 5800, 1.6, 43, 0.28, 18, TEST_VOLTAGE);
  public static final DCMotorModel BaneBots_RS775_18V = new DCMotorModel("BaneBots RS-775 18V", 13050, 2.7, 246, 0.72, 97, TEST_VOLTAGE);
  public static final DCMotorModel BaneBots_RS_550 = new DCMotorModel("BaneBots RS-550", 19000, 0.4, 190, 0.38, 84, TEST_VOLTAGE);

  // From http://www.revrobotics.com/content/docs/REV-21-1651-DS.pdf
  public static final DCMotorModel NEO_550 = new DCMotorModel("NEO 550", 1100, 1.4, 279, 0.97, 100, TEST_VOLTAGE);
}
