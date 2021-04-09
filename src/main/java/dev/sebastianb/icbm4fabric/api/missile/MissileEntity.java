package dev.sebastianb.icbm4fabric.api.missile;

import net.minecraft.util.math.BlockPos;

public interface MissileEntity {
    LaunchStage getStage();
    void setStage(LaunchStage stage);
    int getTimeSinceLastStage();
    void setTimeSinceLastStage(int time);
    BlockPos getInitialPosition();
    void setInitialPosition(BlockPos blockPos);
}
