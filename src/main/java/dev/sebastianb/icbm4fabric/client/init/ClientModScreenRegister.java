package dev.sebastianb.icbm4fabric.client.init;

import dev.sebastianb.icbm4fabric.client.gui.LauncherGUI;
import dev.sebastianb.icbm4fabric.init.ModScreenRegister;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class ClientModScreenRegister {


    public static void register() {
        ScreenRegistry.register(ModScreenRegister.LAUNCHER_SCREEN_HANDLER_SCREEN_HANDLER, LauncherGUI::new);
    }

}
