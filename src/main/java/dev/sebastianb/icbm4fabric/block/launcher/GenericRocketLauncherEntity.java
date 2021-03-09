package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;

public class GenericRocketLauncherEntity extends BlockEntity {



    public GenericRocketLauncherEntity(BlockEntityType<?> type) {
        super(type);
    }

    public GenericRocketLauncherEntity() {
        super(ModBlockEntities.GENERIC_ROCKET_LAUNCHER);
    }
}
