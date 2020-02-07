package org.team4159.frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;

public class DashboardInterface {
  private NetworkTable network_table;

  public DashboardInterface(NetworkTableInstance nt) {
    network_table = nt.getTable("limelight");
  }

  public void periodic() {
    updateDouble("match-time", Timer.getMatchTime());
  }

  public void updateDouble(String key, double value) {
    network_table.getEntry(key).setDouble(value);
  }
}