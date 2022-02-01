package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;

public abstract class AbstractLaunchPath {
    AbstractMissileProjectile rocket;
    public AbstractLaunchPath(AbstractMissileProjectile rocket) {
        this.rocket = rocket;
    }

    public abstract void updateMotion();

    public void updateRotation() {
        double vX = rocket.vX;
        double vY = rocket.vY;
        double vZ = rocket.vZ;
        if (vX == 0 && vY == 0 && vZ == 0) // could be better
            return;

        // below is in radians
        double yaw = Math.atan2(vX, vZ);
        double pitch = Math.atan2(Math.sqrt(Math.pow(vX, 2) + Math.pow(vZ, 2)), vY);

        // radians to degrees
        float realYaw = (float) Math.toDegrees(yaw);
        float realPitch = (float) Math.toDegrees(pitch);

        if (Double.isNaN(realYaw) || Double.isNaN(realPitch)) {
            rocket.setRotation(rocket.prevYaw, rocket.prevPitch); // (To prevent) Invalid entity rotation: NaN, discarding.
            return;
        }
        rocket.setRotation(realYaw, realPitch);

    }
}
