package dev.sebastianb.icbm4fabric;

import java.util.logging.Logger;

import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.item.ModItems;
import dev.sebastianb.icbm4fabric.network.ModPackets;
import dev.sebastianb.icbm4fabric.registries.ModScreenHandlerRegistry;
import dev.sebastianb.icbm4fabric.server.command.ModCommands;
import net.fabricmc.api.ModInitializer;
import rs.neko.mc.ModRegistry;

public class ICBM4Fabric implements ModInitializer {

    public static final Logger LOGGER = Logger.getLogger(Constants.MOD_ID);
    public static final ModRegistry REGISTRY = new ModRegistry(Constants.MOD_ID);


    @Override
    public void onInitialize() {
        ModCommands.register();
        ModBlockEntities.register();
        ModEntityTypes.register();
        ModBlocks.register();
        ModItems.register();
        ModPackets.register();
        ModScreenHandlerRegistry.register();
    }
}
