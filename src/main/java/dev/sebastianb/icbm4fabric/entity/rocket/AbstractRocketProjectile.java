package dev.sebastianb.icbm4fabric.entity.rocket;


import dev.sebastianb.icbm4fabric.SebaUtils;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GravityField;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

public abstract class AbstractRocketProjectile extends MobEntity implements MissileEntity {

    // TODO: Move value to a nbt value (Will likely be cleared on server restart), needs testing
    // IT DOES!!! Put initialLocation in nbt data too
    public double timeSinceStage = 0; // TODO: Move to nbt data

    public BlockPos initialLocation; // ABSOLUTELY DISGUSTING.
    public BlockPos finalLocation = new BlockPos(0,69,0); // TODO: REPLACE WITH A ACTUAL STORED VALUE

    public double vX;
    public double vY;
    public double vZ;

    private static final TrackedData<LaunchStage> STAGE = DataTracker.registerData(AbstractRocketProjectile.class, new TrackedDataHandler<LaunchStage>() {
        @Override
        public void write(PacketByteBuf buf, LaunchStage stage) {
            buf.writeEnumConstant(stage);
        }

        @Override
        public LaunchStage read(PacketByteBuf buf) {
            return buf.readEnumConstant(LaunchStage.class);
        }

        @Override
        public LaunchStage copy(LaunchStage stage) {
            return stage;
        }
    });

    private static final TrackedData<BlockPos> INITIAL_LOCATION = DataTracker.registerData(AbstractRocketProjectile.class, new TrackedDataHandler<BlockPos>() {
        @Override
        public void write(PacketByteBuf buf, BlockPos blockPos) {
            buf.writeBlockPos(blockPos);
        }

        @Override
        public BlockPos read(PacketByteBuf buf) {
            return buf.readBlockPos();
        }

        @Override
        public BlockPos copy(BlockPos blockPos) {
            return blockPos;
        }
    });

    public static final TrackedData<Integer> TIME = DataTracker.registerData(AbstractRocketProjectile.class, TrackedDataHandlerRegistry.INTEGER);


    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        if (tag.contains("Stage")) {
            this.setStage(LaunchStage.valueOf(tag.getString("Stage")));
        }
        if (tag.contains("Time")) {
            this.setTimeSinceLastStage(tag.getInt("Time"));
        }

        this.initialLocation = new BlockPos(tag.getInt("iX"), tag.getInt("iY"), tag.getInt("iZ"));
        this.timeSinceStage = tag.getInt("Time");

    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        tag.putString("Stage", getStage().name());

        // initial block loc coords
        tag.putInt("iX", initialLocation.getX());
        tag.putInt("iY", initialLocation.getY());
        tag.putInt("iZ", initialLocation.getZ());

        // time in ticks from last stage
        tag.putInt("Time", (int) this.timeSinceStage);

    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(STAGE, LaunchStage.IDLE);
        dataTracker.startTracking(INITIAL_LOCATION, new BlockPos(0,0,0));
    }

    static {
        TrackedDataHandlerRegistry.register(STAGE.getType());
        TrackedDataHandlerRegistry.register(INITIAL_LOCATION.getType());
        TrackedDataHandlerRegistry.register(TIME.getType());

    }

    protected AbstractRocketProjectile(EntityType<? extends MobEntity> entityType, World world) {

        super(entityType, world);
    }

    @Override
    public void remove() {
        super.remove();
        System.out.println("despawned");
        // TODO: HANDLE ENTITY PRECALCULATING. Have another thread that handles when to summon the entity then load chunks
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

        double hvel = xfactor* ( 1/Math.sqrt(1+(2*acc*(double)this.timeSinceStage+b)*(2*acc*(double)this.timeSinceStage+b)));


        this.setVelocity(
                (Math.sin(dYaw)*hvel),
                xfactor* ((2*acc*(double)this.timeSinceStage+b)/Math.sqrt(1+(2*acc*(double)this.timeSinceStage+b)*(2*acc*(double)this.timeSinceStage+b))),
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

    @Override
    public LaunchStage getStage() {
        return this.dataTracker.get(STAGE);
    }

    @Override
    public void setStage(LaunchStage stage) {
        if (dataTracker.get(STAGE) != stage) {
            this.dataTracker.set(STAGE, stage);

        }
    }

    @Override
    public int getTimeSinceLastStage() {
        return this.dataTracker.get(TIME);
    }

    @Override
    public void setTimeSinceLastStage(int time) {
        if (dataTracker.get(TIME) != timeSinceStage) {
            this.dataTracker.set(TIME, time);
        }
    }

    @Override
    public BlockPos getInitialPosition() {
        return this.dataTracker.get(INITIAL_LOCATION);
    }

    @Override
    public void setInitialPosition(BlockPos blockPos) {
        this.initialLocation = blockPos;
        //this.dataTracker.set(INITIAL_LOCATION, initialLocation);
    }
}
