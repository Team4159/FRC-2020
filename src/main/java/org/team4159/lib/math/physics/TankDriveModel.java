package org.team4159.lib.math.physics;

import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.geometry.Transform2d;

import org.team4159.lib.control.signal.DriveSignal;

/**
 * Based on https://github.com/robotpy/pyfrc/blob/master/pyfrc/physics/tankmodel.py
 *
 * Assumes:
 * - N motors per side
 * - Constant gearing
 * - Motors geared together
 * - No slip
 * - Rigid body
 */

public class TankDriveModel {
    private DCMotorModelSim m_l_motor, m_r_motor;
    private double m_inertia, m_bm;

    private final double m_timestep = 5.0 * 1000;

    public TankDriveModel(
        double mass_KG,
        double wheelbase_M,
        double width_M,
        double length_M,
        DCMotorModelSim l_motor,
        DCMotorModelSim r_motor
    ) {
        m_l_motor = l_motor;
        m_r_motor = r_motor;

        // Inertia of rectangular prism
        m_inertia = (1 / 12.0) * mass_KG * (length_M * length_M + width_M * width_M);

        m_bm = (wheelbase_M / 2.0) * mass_KG;
    }

    public Transform2d calculate(DriveSignal drive_signal, double dt) {
        double x = 0, y = 0, angle = 0;

        // setting up intervals to perform calcs in
        final double scalar = 100000.0;

        int total_time = (int) (dt * scalar);
        int steps = total_time / (int) m_timestep;
        double step = m_timestep / scalar;

        double last_step;
        double remainder = total_time % m_timestep;
        if (remainder != 0) {
            last_step = remainder / scalar;
            steps += 1;
        } else {
            last_step = step;
        }

        while (steps > 0) {
            final double step_dt = steps == 1 ? last_step : step;

            steps -= 1;

            double l = m_l_motor.compute(drive_signal.left, step_dt),
                   r = m_r_motor.compute(drive_signal.right, step_dt);

            double velocity = (l + r) * 0.5;

            // omega = b * m * (r-l) / J
            double rotation = m_bm * (r - l) / m_inertia;

            double distance = velocity * step_dt;
            double turn = rotation * step_dt;

            x += distance * Math.cos(angle);
            y += distance * Math.sin(angle);

            if (!Double.isNaN(velocity)) {
                System.out.println(velocity);
            }

            angle += turn;
        }

        return new Transform2d(
            new Translation2d(x, y),
            Rotation2d.fromDegrees(angle)
        );
    }
}
