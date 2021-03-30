package dev.sebastianb.icbm4fabric.entity.rocket;


import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GravityField;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractEntityProjectile extends MobEntity {

    private float gravity = 0.007F;

    public BlockPos initialLocation;
    public Vector3d finalLocation = new Vector3d(0,69,0);

    public double vX;
    public double vY;
    public double vZ;

    protected AbstractEntityProjectile(EntityType<? extends MobEntity> entityType, World world) {

        super(entityType, world);
    }



    @Override
    public void tick() {
        super.tick();
        this.updateMotion();
        this.setRotation();


    }

    private void updateMotion() {

        vY -= gravity;

        this.setVelocity(0.2, vY, 0.2);

        this.velocityDirty = false;
        this.velocityModified = true;

    }

    private void setRotation() {
        if (vX == 0 && vY == 0 && vZ == 0) // could be better
            return;

        // below is in radians
        double yaw = Math.atan2(vX, vZ);
        double pitch = Math.atan2(Math.sqrt(Math.pow(vX, 2) + Math.pow(vZ, 2)), vY);

        // radians to degrees
        float realYaw = (float) Math.toDegrees(yaw);
        float realPitch = (float) Math.toDegrees(pitch);


        this.setRotation(realYaw, realPitch);

    }


}
