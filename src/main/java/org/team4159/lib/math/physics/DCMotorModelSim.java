package org.team4159.lib.math.physics;

import org.team4159.lib.math.MathUtil;

public class DCMotorModelSim {
    private final DCMotorModel motor_model;

    public DCMotorModelSim(DCMotorModel motor) {
        motor_model = motor;
    }

    private double acceleration = 0, velocity = 0;

    public double compute(double motor_pct, double dt) {
        // TODO: nominal voltage
        double applied_voltage = motor_pct * 12.0;

        // Heun's method
        // This works apparently but i don't know whats going on
        // yn+1 = yn + (h/2) (f(xn, yn) + f(xn + h, yn +  h f(xn, yn)))
        double a0 = acceleration, v0 = velocity;

        // initial estimate
        double v1 = v0 + a0 * dt;
        double a1 = (applied_voltage - motor_model.volts_per_speed() * v1) * dt /  motor_model.volts_per_speed();

        // trapezoidal correction
        v1 = v0 + ((a0 + a1) / 2.0) * dt;
        a1 = (applied_voltage - motor_model.volts_per_speed() * v1) * dt / motor_model.volts_per_speed();

        // round to prevent runaway errors
//        v1 = MathUtil.epsilonRound(v1);
//        a1 = MathUtil.epsilonRound(a1);

        //System.out.println("motor %: " + motor_pct + ", v0: " + v0  + ", v1: " + v1);

        //position += ((v0 + v1) / 2.0) * dt;
        velocity = v1;
        acceleration = a1;

        return velocity;
    }
}
