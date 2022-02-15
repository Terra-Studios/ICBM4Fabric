package dev.sebastianb.icbm4fabric.entity.missile;

import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import dev.sebastianb.icbm4fabric.entity.missile.path.*;
import dev.sebastianb.icbm4fabric.utils.TrackedDataHandlers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class AbstractMissileProjectile extends Entity implements MissileEntity {

    // spawn packet stuff
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
        player.startRiding(this); // riding missile
        if (player.isSneaking()) {
            setStage(LaunchStage.LIT); // lit the missile (maybe this could be removed at this point?)
        }

        return super.interact(player, hand);
    }

    @Override
    public void setCustomNameVisible(boolean visible) {
        super.setCustomNameVisible(true);
    }

    public double timeSinceStage = 0;

    public BlockPos initialLocation = new BlockPos(0, 0, 0);
//    public BlockPos finalLocation = new BlockPos(0, 69, 0); // nice

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

    private static final TrackedData<Double> TIME = DataTracker.registerData(AbstractMissileProjectile.class, TrackedDataHandlers.DOUBLE);

    private static final TrackedData<BlockPos> INITIAL_BLOCK_POS = DataTracker
            .registerData(AbstractMissileProjectile.class, TrackedDataHandlerRegistry.BLOCK_POS);
    private static final TrackedData<BlockPos> FINAL_BLOCK_POS = DataTracker
            .registerData(AbstractMissileProjectile.class, TrackedDataHandlerRegistry.BLOCK_POS);

    private static final TrackedData<Vec3d> VELOCITY = DataTracker.registerData(AbstractMissileProjectile.class, TrackedDataHandlers.VEC_3D);

    @Override
    public void readCustomDataFromNbt(NbtCompound tag) {
        if (tag.contains("Stage")) {
            this.setStage(LaunchStage.valueOf(tag.getString("Stage"))); //read stage from nbt
        }
        if (tag.contains("Time")) {
            this.timeSinceStage = tag.getDouble("Time"); // read time from nbt
        }

        if (tag.contains("Path")) {
            setPath(LaunchPaths.valueOf(tag.getString("Path"))); // read path form nbt
        }

        int x = tag.getInt("iX");
        int y = tag.getInt("iY");
        int z = tag.getInt("iZ");
        this.setInitialBlockPos(new BlockPos(x, y, z)); // read initial location from nbt

        int fX = tag.getInt("fX");
        int fY = tag.getInt("fY");
        int fZ = tag.getInt("fZ");
        setFinalBlockPos(new BlockPos(fX, fY, fZ)); // read final location from nbt
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound tag) {
        tag.putString("Stage", getStage().name()); // write stage to nbt

        tag.putDouble("Time", this.timeSinceStage); // write time to nbt

        // initial block pos
        tag.putInt("iX", initialLocation.getX());
        tag.putInt("iY", initialLocation.getY());
        tag.putInt("iZ", initialLocation.getZ());

        // final block pos
        tag.putInt("fX", getFinalLocation().getX());
        tag.putInt("fY", getFinalLocation().getY());
        tag.putInt("fZ", getFinalLocation().getZ());

        if (pathType != null) {
            tag.putString("Path", pathType.name()); // write path
        }
    }

    @Override
    protected void initDataTracker() {
        dataTracker.startTracking(STAGE, LaunchStage.IDLE);
        dataTracker.startTracking(TIME, 0.0);
        // dataTracker.startTracking(VELOCITY, BlockPos.ORIGIN); // there's no Vec3d
        // buffer and I'm lazy
        dataTracker.startTracking(INITIAL_BLOCK_POS, BlockPos.ORIGIN); // TODO: Get block pos saved, this code is broken (Is this still needed/broken?)
        dataTracker.startTracking(FINAL_BLOCK_POS, new BlockPos(0, 69, 0));
        dataTracker.startTracking(VELOCITY, this.getPos());
    }

    static {
        TrackedDataHandlerRegistry.register(STAGE.getType());
        TrackedDataHandlerRegistry.register(TIME.getType());
        // TrackedDataHandlerRegistry.register(VELOCITY.getType());
        TrackedDataHandlerRegistry.register(INITIAL_BLOCK_POS.getType()); // TODO: Related to code above 
        TrackedDataHandlerRegistry.register(FINAL_BLOCK_POS.getType());
        TrackedDataHandlerRegistry.register(VELOCITY.getType());
    }

    protected AbstractMissileProjectile(EntityType<? extends AbstractMissileProjectile> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void tick() {
        super.tick();
        if (path != null && updateMotion) {
            if (!world.isClient) {
                path.updateMotion(); // update motion and rotation on server 
                path.updateRotation();
            } else {
                this.setVelocity(dataTracker.get(VELOCITY)); // set velocity from dataTracker
            }
        }
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

    public void setPath(LaunchPaths path) { // might be a better way to do this
        switch (path) {
            case MissingsPath:
                this.path = new MissingsPath(this);
                pathType = LaunchPaths.MissingsPath;
                break;
            case VariableHeightPath:
                this.path = new VariableHeightPath(this, 120);
                pathType = LaunchPaths.VariableHeightPath;
                break;
            case BezierPath:
                this.path = new BezierLaunchPath(this, 120);
                pathType = LaunchPaths.BezierPath;
                break;
        }
    }

    public void setInitialBlockPos(BlockPos blockPos) {
        this.initialLocation = blockPos;
        this.dataTracker.set(INITIAL_BLOCK_POS, blockPos);
    }


    public BlockPos getInitialBlockPos() {
        return dataTracker.get(INITIAL_BLOCK_POS);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.equals(DamageSource.OUT_OF_WORLD)) {
            return super.damage(source, amount); // so /kill works
        }

        return false;
        // return super.damage(source, amount);
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        super.setVelocity(velocity);

        if (!world.isClient) {
            dataTracker.set(VELOCITY, velocity); // set dataTracker on the server
        }

        velocityDirty = false; // not really sure about this (maybe )
    }

    @Override
    public void setRotation(float yaw, float pitch) {
        super.setRotation(yaw, pitch);
    }

    public void setFinalBlockPos(BlockPos target) {
        if (dataTracker.get(STAGE) == LaunchStage.IDLE) {
            dataTracker.set(FINAL_BLOCK_POS, target);
        }
    }

    public BlockPos getFinalLocation() {
        return dataTracker.get(FINAL_BLOCK_POS);
    }
}
