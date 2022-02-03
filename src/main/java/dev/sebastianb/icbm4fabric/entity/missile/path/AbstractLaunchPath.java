package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractLaunchPath {
    AbstractMissileProjectile rocket;
    public AbstractLaunchPath(AbstractMissileProjectile rocket) {
        this.rocket = rocket;
    }

    public abstract void updateMotion();

    public void updateRotation() {
        Vec3d vec3d = rocket.getVelocity();
        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
            return;
        }
        if (rocket.prevPitch == 0.0f && rocket.prevYaw == 0.0f) {
            double d = vec3d.horizontalLength();
            rocket.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
            rocket.setPitch((float) (MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
            rocket.prevYaw = rocket.getYaw();
            rocket.prevPitch = rocket.getPitch();
        }
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double l = vec3d.horizontalLength();
        rocket.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875));
        rocket.setPitch((float) (MathHelper.atan2(f, l) * 57.2957763671875));
        rocket.setPitch(PersistentProjectileEntity.updateRotation(rocket.prevPitch, rocket.getPitch()));
        rocket.setYaw(PersistentProjectileEntity.updateRotation(rocket.prevYaw, rocket.getYaw()));

    }
}
