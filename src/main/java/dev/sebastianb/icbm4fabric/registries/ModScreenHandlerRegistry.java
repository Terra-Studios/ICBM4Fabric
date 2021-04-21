package dev.sebastianb.icbm4fabric.registries;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreen;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.ScreenHandlerProvider;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModScreenHandlerRegistry {


    private static Identifier identifier = new Identifier(Constants.MOD_ID, "launcher_gui");

    public static ScreenHandlerType<? extends LaunchScreenHandler> LAUNCHER_SCREEN =
            ScreenHandlerRegistry.registerSimple(identifier, LaunchScreenHandler::new);


    public static void register() {

    }




}
