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
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractEntityProjectile extends MobEntity {

    private float gravity = 0.035F;
    // private float gravity = 0.3f;

    public double timeSinceStage = 0;



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


        this.setVelocity(Math.cos(this.timeSinceStage / 10) * 1,Math.sin(this.timeSinceStage / 10) * 1, 0);

//        vY -= gravity;
//
//        Vec3d vec = new Vec3d(0.2, vY, 0.2);
//
//        this.setVelocity(vec);
//
//        System.out.println(getVelocity());
//        System.out.println(vec.multiply(1).distanceTo(new Vec3d(0,0,0)));
//
//        this.velocityDirty = false;
//        this.velocityModified = false;

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
