package dev.sebastianb.icbm4fabric.entity.missile.path;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public abstract class AbstractLaunchPath {
    AbstractMissileProjectile missile;
    public AbstractLaunchPath(AbstractMissileProjectile missile) {
        this.missile = missile;
    }

    public abstract void updateMotion();

    public void updateRotation() {
        Vec3d vec3d = missile.getVelocity();
        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
            return;
        }
        if (missile.prevPitch == 0.0f && missile.prevYaw == 0.0f) {
            double d = vec3d.horizontalLength();
            missile.setYaw((float) Math.toRadians(MathHelper.atan2(vec3d.x, vec3d.z)));
            missile.setPitch((float) Math.toRadians(MathHelper.atan2(vec3d.y, d)));
            missile.prevYaw = missile.getYaw();
            missile.prevPitch = missile.getPitch();
        }
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double l = vec3d.horizontalLength();
        missile.setYaw((float) Math.toRadians(MathHelper.atan2(e, g)));
        missile.setPitch((float) Math.toRadians(MathHelper.atan2(f, l)));
        missile.setPitch(PersistentProjectileEntity.updateRotation(missile.prevPitch, missile.getPitch()));
        missile.setYaw(PersistentProjectileEntity.updateRotation(missile.prevYaw, missile.getYaw()));

    }
}
