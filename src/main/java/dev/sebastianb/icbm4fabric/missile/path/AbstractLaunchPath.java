package dev.sebastianb.icbm4fabric.missile.path;

import dev.sebastianb.icbm4fabric.missile.Missile;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractLaunchPath {
    public Missile missile;
    public AbstractLaunchPath(Missile missile) {
        this.missile = missile;
    }

    public abstract void updateMotion();

    public void updateRotation() {
        Vec3d velocity = missile.getMissileData().getVelocity();

        if (Double.isNaN(velocity.getX()) || Double.isNaN(velocity.getY()) || Double.isNaN(velocity.getZ())) {
            return;
        }

        double horizontalVelocity = velocity.horizontalLength();

        missile.getMissileData().setYaw((float) Math.toDegrees(Math.atan2(velocity.getX(), velocity.getZ())));
        missile.getMissileData().setPitch((float) Math.toDegrees(Math.atan2(velocity.getY(), horizontalVelocity)));
    }
}
