package dev.sebastianb.icbm4fabric.api.missile;

import dev.sebastianb.icbm4fabric.entity.rocket.path.LaunchPaths;

public interface MissileEntity {
    LaunchStage getStage();
    void setStage(LaunchStage stage);

    void setPath(LaunchPaths path);

}
