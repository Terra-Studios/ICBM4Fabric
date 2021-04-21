package dev.sebastianb.icbm4fabric.init;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.client.gui.LauncherScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenRegister {

    public static final ScreenHandlerType<LauncherScreenHandler> LAUNCHER_SCREEN_HANDLER_SCREEN_HANDLER;

    private static final Identifier identifier = new Identifier(Constants.MOD_ID, Constants.Blocks.TIER_1_MISSILE_LAUNCHER);

    static {
        LAUNCHER_SCREEN_HANDLER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(identifier, LauncherScreenHandler::new);
    }


    public static void register() {

    }

}
