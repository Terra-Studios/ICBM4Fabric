package dev.sebastianb.icbm4fabric.entity.rocket;


import dev.sebastianb.icbm4fabric.SebaUtils;
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

public abstract class AbstractRocketProjectile extends MobEntity {


    public double timeSinceStage = 0;



    public BlockPos initialLocation;
    public BlockPos finalLocation = new BlockPos(0,69,0);

    public double vX;
    public double vY;
    public double vZ;

    protected AbstractRocketProjectile(EntityType<? extends MobEntity> entityType, World world) {

        super(entityType, world);
    }



    @Override
    public void tick() {
        super.tick();
        this.updateMotion();
        this.setRotation();


    }



    private void updateMotion() {

        double acc = -0.01;

        double dX = finalLocation.getX() - initialLocation.getX();
        double dY = finalLocation.getY() - initialLocation.getY();
        double dZ = finalLocation.getZ() - initialLocation.getZ();


        double dYaw = Math.atan2(dX, dZ);

        double groundDistTravelled = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2)); // gets distance traveled horizontally

        double b = -acc*groundDistTravelled + (dY/groundDistTravelled);

        double temp1 = (1/(2*acc)) * SebaUtils.asinh(2*acc*groundDistTravelled+b);
        double temp2 = (1/(2*acc)) * SebaUtils.asinh(b);

        double xfactor = groundDistTravelled/(temp1-temp2); // * by any num to adjust speed

        double hvel = xfactor* ( 1/Math.sqrt(1+(2*acc*this.timeSinceStage+b)*(2*acc*this.timeSinceStage+b)));


        this.setVelocity(
                (Math.sin(dYaw)*hvel),
                xfactor* ((2*acc*this.timeSinceStage+b)/Math.sqrt(1+(2*acc*this.timeSinceStage+b)*(2*acc*this.timeSinceStage+b))),
                (Math.cos(dYaw)*hvel)
        );

//        this.setVelocity(
//                Math.sin(this.timeSinceStage / (diameter / speed)) * speed,
//                Math.cos(this.timeSinceStage / (diameter / speed)) * speed,
//                0);
                //Math.sin(this.timeSinceStage / ((diameter / 2) / (speed / 2))) * (speed / 2));
                // trying to understand how I can put Z in this but seems to work somewhat. I just dunno how to control
                // divided by 2 across everything seems to work. Maybe if I can get the X then do math with what I need to divide, it'd work.
                // best approach I think is to get a velocity vector req to launch from superclass for later. Right now I just have these temp variables

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
