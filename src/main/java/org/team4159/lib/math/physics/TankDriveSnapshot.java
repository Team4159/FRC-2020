package org.team4159.lib.math.physics;

import edu.wpi.first.wpilibj.geometry.Transform2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;

public class TankDriveSnapshot {
    public final Transform2d transform2d;
    public final DifferentialDriveWheelSpeeds wheel_speeds;

    public TankDriveSnapshot(Transform2d transform2d, DifferentialDriveWheelSpeeds wheel_speeds) {
        this.transform2d = transform2d;
        this.wheel_speeds = wheel_speeds;
    }
}
