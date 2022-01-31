package dev.sebastianb.icbm4fabric.registries;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerRegistry {

    private static final Identifier identifier = new Identifier(Constants.MOD_ID, "launcher_gui");

    public static ScreenHandlerType<? extends LaunchScreenHandler> LAUNCHER_SCREEN =
            ScreenHandlerRegistry.registerSimple(identifier, LaunchScreenHandler::new);


    public static void register() {

    }

}
