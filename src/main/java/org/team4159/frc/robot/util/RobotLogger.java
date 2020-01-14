package org.team4159.frc.robot.util;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;

import badlog.lib.BadLog;
import badlog.lib.InvalidModeException;

import org.team4159.frc.robot.subsystems.LoggableSubsystem;


// TODO: Call loggingPeriodic periodically
public class RobotLogger {
  @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
  private static Optional<RobotLogger> instance;

  public static RobotLogger getInstance() {
    if (instance.isEmpty()) {
      instance = Optional.of(new RobotLogger());
    }
    return instance.get();
  }

  private BadLog log;
  private HashSet<LoggableSubsystem> subsystems;
  private boolean badLogInitialized = false;

  private RobotLogger() {
    log = BadLog.init(generateHexString() + ".bag");
    subsystems = new HashSet<>();
  }

  public void addSubsystem(LoggableSubsystem subsystem) {
    if (badLogInitialized)
      throw new InvalidModeException();
    subsystems.add(subsystem);
  }

  public void removeSubsystem(LoggableSubsystem subsystem) {
    subsystems.remove(subsystem);
  }

  public void finishInitialization() {
    if (badLogInitialized)
      throw new InvalidModeException();

    for (LoggableSubsystem subsystem : subsystems) {
      subsystem.loggingInit();
    }
    badLogInitialized = true;

    log.finishInitialization();
  }

  private String generateHexString() {
    Random random = new Random();
    return Integer.toHexString(random.nextInt()).toUpperCase();
  }
}
