package org.team4159.lib.hardware.motors;

public class Motors {
  // From https://motors.vex.com/
  public static final MotorData Falcon_500 = new MotorData("Falcon 500", 6380, 1.5, 783, 4.69, 257);
  public static final MotorData NEO = new MotorData("NEO", 5880, 1.3, 516, 3.36, 166);
  public static final MotorData CIM = new MotorData("CIM", 5330, 2.7, 337, 2.41, 131);
  public static final MotorData MiniCIM = new MotorData("Mini CIM",  5840, 3, 215, 1.41, 89);
  public static final MotorData BAG = new MotorData("BAG", 13180, 1.8, 149, 0.43, 53);
  public static final MotorData _775pro = new MotorData("775pro", 18730, 0.7, 347, 0.71, 134);
  public static final MotorData AndyMark_9015 = new MotorData("AndyMark 9015", 14270, 3.7, 134, 0.36, 71);
  public static final MotorData AndyMark_NeveRest = new MotorData("AndyMark NeveRest", 5480, 0.4, 25, 0.17, 10);
  public static final MotorData AndyMark_RS775_125 = new MotorData("AndyMark RS775-125", 5800, 1.6, 43, 0.28, 18);
  public static final MotorData BaneBots_RS775_18V = new MotorData("BaneBots RS-775 18V", 13050, 2.7, 246, 0.72, 97);
  public static final MotorData BaneBots_RS_550 = new MotorData("BaneBots RS-550", 19000, 0.4, 190, 0.38, 84);

  // From http://www.revrobotics.com/content/docs/REV-21-1651-DS.pdf
  public static final MotorData NEO_550 = new MotorData("NEO 550", 1100, 1.4, 279, 0.97, 100);
}
