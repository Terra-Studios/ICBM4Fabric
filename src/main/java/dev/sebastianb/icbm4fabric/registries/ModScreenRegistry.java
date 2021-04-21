package dev.sebastianb.icbm4fabric.registries;

import dev.sebastianb.icbm4fabric.client.gui.LaunchScreen;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ModScreenRegistry {

    public static void registerScreens() {

        ScreenRegistry.register(ModScreenHandlerRegistry.LAUNCHER_SCREEN, LaunchScreen::new);

    }

}
