package dev.sebastianb.icbm4fabric.entity.rocket;


import dev.sebastianb.icbm4fabric.SebaUtils;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractRocketProjectile extends MobEntity implements MissileEntity {



    public double timeSinceStage = 0;

    public BlockPos initialLocation = new BlockPos(0,0,0);
    public BlockPos finalLocation = new BlockPos(0,69,0);

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

    private static final TrackedData<Double> TIME = DataTracker.registerData(AbstractRocketProjectile.class, new TrackedDataHandler<Double>() {
        @Override
        public void write(PacketByteBuf buf, Double time) {
            buf.writeDouble(time);
        }

        @Override
        public Double read(PacketByteBuf buf) {
            return buf.readDouble();
        }

        @Override
        public Double copy(Double time) {
            return time;
        }
    });

//    private static final TrackedData<BlockPos> VELOCITY = DataTracker.registerData(AbstractRocketProjectile.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<BlockPos> INITIAL_BLOCK_POS = DataTracker.registerData(AbstractRocketProjectile.class, TrackedDataHandlerRegistry.BLOCK_POS);

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        if (tag.contains("Stage")) {
            this.setStage(LaunchStage.valueOf(tag.getString("Stage")));
        }
        if (tag.contains("Time")) {
            this.timeSinceStage = tag.getDouble("Time");
        }

//        double vX = tag.getDouble("vX");
//        double vY = tag.getDouble("vX");
//        double vZ = tag.getDouble("vZ");
//        this.setVelocity(new Vec3d(vX, vY, vZ));


        int x = tag.getInt("iX");
        int y = tag.getInt("iY");
        int z = tag.getInt("iZ");
        this.setInitialBlockPos(new BlockPos(x,y,z));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        tag.putString("Stage", getStage().name());

        tag.putDouble("Time", this.timeSinceStage);

//        // velocity saved
//        tag.putDouble("vX", this.vX);
//        tag.putDouble("vY", this.vY);
//        tag.putDouble("vZ", this.vZ);

        // initial block pos
        tag.putInt("iX", initialLocation.getX());
        tag.putInt("iY", initialLocation.getY());
        tag.putInt("iZ", initialLocation.getZ());
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(STAGE, LaunchStage.IDLE);
        dataTracker.startTracking(TIME, 0.0);
//        dataTracker.startTracking(VELOCITY, BlockPos.ORIGIN); // there's no Vec3d buffer and I'm lazy
        dataTracker.startTracking(INITIAL_BLOCK_POS, BlockPos.ORIGIN); // TODO: Get block pos saved, this code is broken
    }


    static {
        TrackedDataHandlerRegistry.register(STAGE.getType());
        TrackedDataHandlerRegistry.register(TIME.getType());
        // TrackedDataHandlerRegistry.register(VELOCITY.getType());
        TrackedDataHandlerRegistry.register(INITIAL_BLOCK_POS.getType()); // TODO: Related to code above
    }

    protected AbstractRocketProjectile(EntityType<? extends MobEntity> entityType, World world) {

        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        this.updateMotion();
        this.setRotation();

    }

    // TODO: Have some values fed upon init
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

        if (Double.isNaN(realYaw) || Double.isNaN(realPitch)) {
            this.setRotation(prevYaw, prevPitch); // (To prevent) Invalid entity rotation: NaN, discarding.
            return;
        }
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
            timeSinceStage = 0;
        }
    }


    public void setInitialBlockPos(BlockPos blockPos) {
        this.initialLocation = blockPos;
        this.dataTracker.set(INITIAL_BLOCK_POS, blockPos);
    }

//    public void setVelocity(Vec3d vector) {
//        this.vX = vector.x;
//        this.vY = vector.y;
//        this.vZ = vector.z;
//        this.dataTracker.set(INITIAL_BLOCK_POS, new BlockPos(vector));
//    }

    public BlockPos getInitialBlockPos() {
        return dataTracker.get(INITIAL_BLOCK_POS);
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
        super.takeKnockback(0, 0, 0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.equals(DamageSource.OUT_OF_WORLD)) {
            return super.damage(source, amount);
        }

        return false;
        // return super.damage(source, amount);
    }

}
