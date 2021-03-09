package dev.sebastianb.icbm4fabric.client;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.init.ModClientEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
public class Icbm4fabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClientEntityRegister.register();
    }
}
