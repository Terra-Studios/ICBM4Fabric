package dev.sebastianb.icbm4fabric.entity.rocket.path;

import dev.sebastianb.icbm4fabric.SebaUtils;
import dev.sebastianb.icbm4fabric.entity.rocket.AbstractRocketProjectile;

public class MissingsPath extends AbstractLaunchPath{

    double acc;

    double dX;
    double dY;
    double dZ;

    double dYaw;

    public MissingsPath(AbstractRocketProjectile rocket) {
        super(rocket);

        acc = -0.01; // gravity acceleration. Should be negative

        dX = rocket.finalLocation.getX() - rocket.initialLocation.getX();
        dY = rocket.finalLocation.getY() - rocket.initialLocation.getY();
        dZ = rocket.finalLocation.getZ() - rocket.initialLocation.getZ();

        dYaw = Math.atan2(dX, dZ);
    }

    @Override
    public void updateMotion() {
        double groundDistTravelled = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2)); // gets distance traveled horizontally

        double b = - acc * groundDistTravelled + (dY / groundDistTravelled);

        double temp1 = (1 / (2 * acc)) * SebaUtils.asinh(2 * acc * groundDistTravelled + b);
        double temp2 = (1 / (2 * acc)) * SebaUtils.asinh(b);

        double xfactor = groundDistTravelled / (temp1 - temp2); // * by any num to adjust speed.

        double hvel = xfactor * (1 / Math.sqrt(1 + (2 * acc * rocket.timeSinceStage + b) * (2 * acc * rocket.timeSinceStage + b)));

        rocket.setVelocity(
                (Math.sin(dYaw) * hvel),
                xfactor * ((2 * acc * rocket.timeSinceStage + b) / Math.sqrt(1 + (2 * acc * rocket.timeSinceStage + b) * (2 * acc * rocket.timeSinceStage + b))),
                (Math.cos(dYaw) * hvel)
        );
    }
}
