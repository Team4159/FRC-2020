package org.team4159.lib.control.signal;

import static org.team4159.lib.control.signal.SignalUtil.*;

public class DriveSignal {
  public double left, right;

  public static DriveSignal NEUTRAL = new DriveSignal(0, 0);

  public DriveSignal(double left, double right) {
    this.left = clampToPercent(left);
    this.right = clampToPercent(right);
  }

  public DriveSignal(double left, double right, boolean squared) {
    this(
      squared ? squareWithSign(clampToPercent(left)) : left,
      squared ? squareWithSign(clampToPercent(right)) : right
    );
  }

  public static DriveSignal fromArcade(double speed, double turn) {
    return new DriveSignal(
      clampToPercent(speed) - clampToPercent(turn),
      clampToPercent(speed) + clampToPercent(turn)
    );
  }

  public static DriveSignal fromArcade(double speed, double turn, boolean squared) {
    return fromArcade(
      squared ? squareWithSign(clampToPercent(speed)) : speed,
      squared ? squareWithSign(clampToPercent(turn)) : turn
    );
  }

  public static DriveSignal fromVolts(double left, double right) {
    return new DriveSignal(
      voltsToPercentOutput(left),
      voltsToPercentOutput(right)
    );
  }
}
