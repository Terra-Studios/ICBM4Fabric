package dev.sebastianb.icbm4fabric.entity.missile;

import dev.sebastianb.icbm4fabric.SebaUtils;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import dev.sebastianb.icbm4fabric.entity.missile.path.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractMissileProjectile extends Entity implements MissileEntity {

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
    }

    AbstractLaunchPath path;
    LaunchPaths pathType;

    @Override
    public boolean collides() {
        return true;
    }

    @Override
    public boolean isAttackable() {
        return true;
    }


    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (!this.isAlive()) {
            return ActionResult.PASS;
        }
        player.startRiding(this);
        if (player.isSneaking()) {
            setStage(LaunchStage.LIT);
        }
        return super.interact(player, hand);
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        super.setCustomNameVisible(true);
    }

    public double timeSinceStage = 0;

    public BlockPos initialLocation = new BlockPos(0, 0, 0);
    public BlockPos finalLocation = new BlockPos(0, 69, 0); // nice

    public double vX;
    public double vY;
    public double vZ;

    public boolean updateMotion;

    private static final TrackedData<LaunchStage> STAGE = DataTracker.registerData(AbstractMissileProjectile.class,
            new TrackedDataHandler<LaunchStage>() {
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

    private static final TrackedData<Double> TIME = DataTracker.registerData(AbstractMissileProjectile.class,
            new TrackedDataHandler<Double>() {
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

    // private static final TrackedData<BlockPos> VELOCITY =
    // DataTracker.registerData(AbstractRocketProjectile.class,
    // TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<BlockPos> INITIAL_BLOCK_POS = DataTracker
            .registerData(AbstractMissileProjectile.class, TrackedDataHandlerRegistry.BLOCK_POS);

    private static final TrackedData<Vec3d> TARGET_POS = DataTracker.registerData(AbstractMissileProjectile.class, new TrackedDataHandler<Vec3d>() {
        @Override
        public void write(PacketByteBuf buf, Vec3d value) {
            buf.writeDouble(value.getX());
            buf.writeDouble(value.getY());
            buf.writeDouble(value.getZ());
        }

        @Override
        public Vec3d read(PacketByteBuf buf) {
            return new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        }

        @Override
        public Vec3d copy(Vec3d value) {
            return value;
        }
    });

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        if (tag.contains("Stage")) {
            this.setStage(LaunchStage.valueOf(tag.getString("Stage")));
        }
        if (tag.contains("Time")) {
            this.timeSinceStage = tag.getDouble("Time");
        }

        if (tag.contains("Path")) {
            setPath(LaunchPaths.valueOf(tag.getString("Path")));
        }

        // double vX = tag.getDouble("vX");
        // double vY = tag.getDouble("vX");
        // double vZ = tag.getDouble("vZ");
        // this.setVelocity(new Vec3d(vX, vY, vZ));

        int x = tag.getInt("iX");
        int y = tag.getInt("iY");
        int z = tag.getInt("iZ");
        this.setInitialBlockPos(new BlockPos(x, y, z));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        tag.putString("Stage", getStage().name());

        tag.putDouble("Time", this.timeSinceStage);

        // // velocity saved
        // tag.putDouble("vX", this.vX);
        // tag.putDouble("vY", this.vY);
        // tag.putDouble("vZ", this.vZ);

        // initial block pos
        tag.putInt("iX", initialLocation.getX());
        tag.putInt("iY", initialLocation.getY());
        tag.putInt("iZ", initialLocation.getZ());

        if (pathType != null) {
            tag.putString("Path", pathType.name());
        }
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(STAGE, LaunchStage.IDLE);
        dataTracker.startTracking(TIME, 0.0);
        // dataTracker.startTracking(VELOCITY, BlockPos.ORIGIN); // there's no Vec3d
        // buffer and I'm lazy
        dataTracker.startTracking(INITIAL_BLOCK_POS, BlockPos.ORIGIN); // TODO: Get block pos saved, this code is broken
        dataTracker.startTracking(TARGET_POS, this.getPos());
    }

    static {
        TrackedDataHandlerRegistry.register(STAGE.getType());
        TrackedDataHandlerRegistry.register(TIME.getType());
        // TrackedDataHandlerRegistry.register(VELOCITY.getType());
        TrackedDataHandlerRegistry.register(INITIAL_BLOCK_POS.getType()); // TODO: Related to code above
        TrackedDataHandlerRegistry.register(TARGET_POS.getType());
    }

    protected AbstractMissileProjectile(EntityType<? extends AbstractMissileProjectile> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (path != null && updateMotion) {
            if (world.isClient) {
                Vec3d targetPos = dataTracker.get(TARGET_POS);

                setVelocity(targetPos.getX() - getX(), targetPos.getY() - getY(), targetPos.getZ() - getZ());
            } else {
                path.updateMotion();
            }
            path.updateRotation();
        }
    }

    // TODO: Have some values fed upon init
    private void updateMotion() {

        double acc = -0.01; // gravity acceleration. Should be negative

        double dX = finalLocation.getX() - initialLocation.getX();
        double dY = finalLocation.getY() - initialLocation.getY();
        double dZ = finalLocation.getZ() - initialLocation.getZ();

        double dYaw = Math.atan2(dX, dZ);

        double groundDistTravelled = Math.sqrt(Math.pow(dX, 2) + Math.pow(dZ, 2)); // gets distance traveled
                                                                                   // horizontally

        double b = -acc * groundDistTravelled + (dY / groundDistTravelled);

        double temp1 = (1 / (2 * acc)) * SebaUtils.asinh(2 * acc * groundDistTravelled + b);
        double temp2 = (1 / (2 * acc)) * SebaUtils.asinh(b);

        double xfactor = groundDistTravelled / (temp1 - temp2); // * by any num to adjust speed.

        double hvel = xfactor
                * (1 / Math.sqrt(1 + (2 * acc * this.timeSinceStage + b) * (2 * acc * this.timeSinceStage + b)));

        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(
                (Math.sin(dYaw) * hvel),
                xfactor * ((2 * acc * this.timeSinceStage + b)
                        / Math.sqrt(1 + (2 * acc * this.timeSinceStage + b) * (2 * acc * this.timeSinceStage + b))),
                (Math.cos(dYaw) * hvel));
        // this.setVelocity(
        // Math.sin(this.timeSinceStage / (diameter / speed)) * speed,
        // Math.cos(this.timeSinceStage / (diameter / speed)) * speed,
        // 0);
        // Math.sin(this.timeSinceStage / ((diameter / 2) / (speed / 2))) * (speed /
        // 2));
        // trying to understand how I can put Z in this but seems to work somewhat. I
        // just dunno how to control
        // divided by 2 across everything seems to work. Maybe if I can get the X then
        // do math with what I need to divide, it'd work.
        // best approach I think is to get a velocity vector req to launch from
        // superclass for later. Right now I just have these temp variables

    }

    private void setRotation() {
        Vec3d vec3d = this.getVelocity();
        if (Double.isNaN(vec3d.x) || Double.isNaN(vec3d.y) || Double.isNaN(vec3d.z)) {
            return;
        }
        if (this.prevPitch == 0.0f && this.prevYaw == 0.0f) {
            double d = vec3d.horizontalLength();
            this.setYaw((float) (MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
            this.setPitch((float) (MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
            this.prevYaw = this.getYaw();
            this.prevPitch = this.getPitch();
        }
        double e = vec3d.x;
        double f = vec3d.y;
        double g = vec3d.z;
        double l = vec3d.horizontalLength();
        this.setYaw((float) (MathHelper.atan2(e, g) * 57.2957763671875));
        this.setPitch((float) (MathHelper.atan2(f, l) * 57.2957763671875));
//        this.setPitch(PersistentProjectileEntity.updateRotation(this.prevPitch, this.getPitch()));
//        this.setYaw(PersistentProjectileEntity.updateRotation(this.prevYaw, this.getYaw()));

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

    public void setPath(LaunchPaths path) {
        switch (path) {
            case MissingsPath:
                this.path = new MissingsPath(this);
                pathType = LaunchPaths.MissingsPath;
                break;
            case VaribleHeightPath:
                this.path = new VariableHeightPath(this, 120);
                pathType = LaunchPaths.VaribleHeightPath;
                break;
            case BezierPath:
                this.path = new BezierLaunchPath(this, 100);
                pathType = LaunchPaths.BezierPath;
        }
    }

    public void setInitialBlockPos(BlockPos blockPos) {
        this.initialLocation = blockPos;
        this.dataTracker.set(INITIAL_BLOCK_POS, blockPos);
    }

    // public void setVelocity(Vec3d vector) {
    // this.vX = vector.x;
    // this.vY = vector.y;
    // this.vZ = vector.z;
    // this.dataTracker.set(INITIAL_BLOCK_POS, new BlockPos(vector));
    // }

    public BlockPos getInitialBlockPos() {
        return dataTracker.get(INITIAL_BLOCK_POS);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.equals(DamageSource.OUT_OF_WORLD)) {
            return super.damage(source, amount);
        }

        return false;
        // return super.damage(source, amount);
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        super.setVelocity(velocity);

        if (!world.isClient) {
            dataTracker.set(TARGET_POS, velocity);
        }

        velocityDirty = false;
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
    }
}
