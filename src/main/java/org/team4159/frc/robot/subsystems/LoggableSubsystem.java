package org.team4159.frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4159.frc.robot.util.RobotLogger;

public abstract class LoggableSubsystem extends SubsystemBase {
  public LoggableSubsystem() {
    super();
    RobotLogger.getInstance().addSubsystem(this);
  }

  public void loggingInit() {
  };

  public void loggingPeriodic() {
  };
}
