package dev.sebastianb.icbm4fabric.client;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.init.ModClientEntityRegister;
import dev.sebastianb.icbm4fabric.registries.ModScreenRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class Icbm4fabricClient implements ClientModInitializer {

    public static final EntityModelLayer TATER_LAYER = new EntityModelLayer(new Identifier(Constants.MOD_ID, "tater_render_layer"), "tater_render_layer");

    @Override
    public void onInitializeClient() {
        ModClientEntityRegister.register();
        ModScreenRegistry.registerScreens();
    }
}
