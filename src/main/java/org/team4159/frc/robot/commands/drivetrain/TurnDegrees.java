package org.team4159.frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import org.team4159.frc.robot.subsystems.Drivetrain;

import static org.team4159.frc.robot.Constants.*;


public class TurnDegrees extends CommandBase {
  private Drivetrain drivetrain;
  private double initial_degrees;

  private PIDController controller = new PIDController(DRIVE_CONSTANTS.kP, 0, 0);

  public TurnDegrees(double theta_degrees, Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    controller.setSetpoint(theta_degrees);

    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    initial_degrees = drivetrain.getDirection();
  }

  @Override
  public void execute() {
    double output = controller.calculate(drivetrain.getDirection() - initial_degrees);

    //drivetrain.voltsDrive(output, -output);
  }

  @Override
  public boolean isFinished() {
    return controller.atSetpoint();
  }
}
