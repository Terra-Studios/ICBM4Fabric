package dev.sebastianb.icbm4fabric.client.init;

import dev.sebastianb.icbm4fabric.client.renderer.entity.rocket.TaterRocketRenderer;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

public class ModClientEntityRegister {
    public static void register() {
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TATER_ROCKET, (dispatcher, context)
                -> new TaterRocketRenderer(dispatcher));
    }
}
