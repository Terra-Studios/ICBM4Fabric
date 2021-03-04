package dev.sebastianb.icbm4fabric;

import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.entity.ModEntities;
import net.fabricmc.api.ModInitializer;

public class ICBM4Fabric implements ModInitializer {
    @Override
    public void onInitialize() {
        ModEntities.register();
        ModBlocks.register();
    }
}
