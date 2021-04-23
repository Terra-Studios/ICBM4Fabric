package dev.sebastianb.icbm4fabric.client;

import dev.sebastianb.icbm4fabric.client.init.ModClientEntityRegister;
import dev.sebastianb.icbm4fabric.registries.ModScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Icbm4fabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModClientEntityRegister.register();
        ModScreenRegistry.registerScreens();
    }
}
