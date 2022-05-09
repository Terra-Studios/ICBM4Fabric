package dev.sebastianb.icbm4fabric.missile;

import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.entity.missile.path.LaunchPaths;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class MissileData { // class for storing data for missiles
    private MissileDataPersistentState missileDataPersistentState;

    private BlockPos initialPos, targetPos;

    private Vec3d pos, velocity;

    private float pitch, yaw;

    private int timeSinceStage;

    private LaunchStage stage;

    private LaunchPaths path;

    public void setInitialPos(BlockPos pos) {
        this.initialPos = pos;
        this.markDirty();
    }

    public BlockPos getInitialPos() {
        return this.initialPos;
    }

    public void setTargetPos(BlockPos pos) {
        this.targetPos = pos;
        this.markDirty();
    }

    public BlockPos getTargetPos() {
        return this.targetPos;
    }

    public void setPos(Vec3d pos) {
        this.pos = pos;
        this.markDirty();
    }

    public Vec3d getPos() {
        return this.pos;
    }

    public BlockPos getBlockPos() {
        return new BlockPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
        this.markDirty();
    }

    public Vec3d getVelocity() {
        return this.velocity;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
        this.markDirty();
    }

    public float getPitch() {
        return pitch;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
        this.markDirty();
    }

    public float getYaw() {
        return yaw;
    }

    public void setStage(LaunchStage stage) {
        this.stage = stage;
        this.setTimeSinceStage(0);
        this.markDirty();
    }

    public LaunchPaths getPath() {
        return path;
    }

    public void getPath(LaunchPaths path) {
        this.path = path;
    }

    public LaunchStage getStage() {
        return stage;
    }

    public int getTimeSinceStage() {
        return timeSinceStage;
    }

    public void setTimeSinceStage(int time) {
        timeSinceStage = time;
    }

    public MissileData(NbtCompound nbt, MissileDataPersistentState missileDataPersistentState) {
        initialPos = new BlockPos(nbt.getInt("initial_x"), nbt.getInt("initial_y"), nbt.getInt("initial_z"));
        targetPos = new BlockPos(nbt.getInt("target_x"), nbt.getInt("target_y"), nbt.getInt("target_z"));

        pos = new Vec3d(nbt.getDouble("pos_x"), nbt.getDouble("pos_y"), nbt.getDouble("pos_z"));
        velocity = new Vec3d(nbt.getDouble("velocity_x"), nbt.getDouble("velocity_y"), nbt.getDouble("velocity_z"));

        pitch = nbt.getFloat("pitch");
        yaw = nbt.getFloat("yaw");

        timeSinceStage = nbt.getInt("time");

        stage = LaunchStage.values()[nbt.getInt("launchStage")];

        path = LaunchPaths.values()[nbt.getInt("path")];

        this.missileDataPersistentState = missileDataPersistentState;
    }

    public MissileData(MissileDataPersistentState missileDataPersistentState) {
        this.missileDataPersistentState = missileDataPersistentState;

        targetPos = BlockPos.ORIGIN;
        initialPos = BlockPos.ORIGIN;
        pos = Vec3d.ZERO;
        velocity = Vec3d.ZERO;
        stage = LaunchStage.IDLE;
    }

    public void markDirty() {
        missileDataPersistentState.markDirty();
    }

    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("initial_x", initialPos.getX());
        nbt.putInt("initial_y", initialPos.getY());
        nbt.putInt("initial_z", initialPos.getZ());

        nbt.putInt("target_x", targetPos.getX());
        nbt.putInt("target_y", targetPos.getY());
        nbt.putInt("target_z", targetPos.getZ());

        nbt.putDouble("pos_x", pos.getX());
        nbt.putDouble("pos_y", pos.getY());
        nbt.putDouble("pos_z", pos.getZ());

        nbt.putDouble("velocity_x", velocity.getX());
        nbt.putDouble("velocity_y", velocity.getY());
        nbt.putDouble("velocity_z", velocity.getZ());

        nbt.putFloat("pitch", pitch);
        nbt.putFloat("yaw", yaw);

        nbt.putInt("time", timeSinceStage);

        nbt.putInt("launchStage", stage.ordinal());

        if (path == null) {
            nbt.putInt("path", 0);
        } else {
            nbt.putInt("path", path.ordinal());
        }

        return nbt;
    }
}
