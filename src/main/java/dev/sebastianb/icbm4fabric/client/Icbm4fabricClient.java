package dev.sebastianb.icbm4fabric.client;

import dev.sebastianb.icbm4fabric.client.init.ClientModScreenRegister;
import dev.sebastianb.icbm4fabric.client.init.ClientModEntityRegister;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Icbm4fabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModEntityRegister.register();
        ClientModScreenRegister.register();
    }
}
