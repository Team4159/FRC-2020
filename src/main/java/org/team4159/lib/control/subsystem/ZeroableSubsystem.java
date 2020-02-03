package org.team4159.lib.control.subsystem;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ZeroableSubsystem extends SubsystemBase {
  protected boolean is_zeroed = false;

  public ZeroableSubsystem() {

  }

  public void zeroSubsystem() {
    is_zeroed = true;
  }

  public boolean isSubsystemZeroed() {
    return is_zeroed;
  }
}
