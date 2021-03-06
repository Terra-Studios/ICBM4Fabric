package dev.sebastianb.icbm4fabric;

import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.entity.ModEntities;
import dev.sebastianb.icbm4fabric.server.command.ModCommands;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class ICBM4Fabric implements ModInitializer {

    public static final Logger LOGGER = Logger.getLogger(Constants.MOD_ID);


    @Override
    public void onInitialize() {

        ModCommands.register();
        ModEntities.register();
        ModBlocks.register();
    }
}
