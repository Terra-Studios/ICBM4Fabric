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
            return; // make sure all parts of velocity are real
        }

        if (missile.prevPitch == 0.0f && missile.prevYaw == 0.0f) { // from arrow code
            double horizontalVelocityLength = vec3d.horizontalLength();
            missile.setYaw((float) Math.toDegrees(MathHelper.atan2(vec3d.x, vec3d.z)));
            missile.setPitch((float) Math.toDegrees(MathHelper.atan2(vec3d.y, horizontalVelocityLength)));
            missile.prevYaw = missile.getYaw();
            missile.prevPitch = missile.getPitch();
        }
        double xVel = vec3d.x;
        double yVel = vec3d.y;
        double zVel = vec3d.z;
        double horizontalVelocityLength = vec3d.horizontalLength();
        missile.setYaw((float) Math.toDegrees(MathHelper.atan2(xVel, zVel)));
        missile.setPitch((float) Math.toDegrees(MathHelper.atan2(yVel, horizontalVelocityLength)));
        missile.setPitch(PersistentProjectileEntity.updateRotation(missile.prevPitch, missile.getPitch()));
        missile.setYaw(PersistentProjectileEntity.updateRotation(missile.prevYaw, missile.getYaw()));

    }
}
