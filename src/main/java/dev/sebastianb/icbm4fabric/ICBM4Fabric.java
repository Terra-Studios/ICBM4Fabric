package dev.sebastianb.icbm4fabric;

import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.init.ModScreenRegister;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.network.ModPackets;
import dev.sebastianb.icbm4fabric.server.command.ModCommands;
import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class ICBM4Fabric implements ModInitializer {

    public static final Logger LOGGER = Logger.getLogger(Constants.MOD_ID);


    @Override
    public void onInitialize() {
        ModScreenRegister.register();
        ModCommands.register();
        ModBlockEntities.register();
        ModEntityTypes.register();
        ModBlocks.register();
        ModPackets.register();

    }
}
