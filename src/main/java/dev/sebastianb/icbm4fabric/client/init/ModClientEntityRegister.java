package dev.sebastianb.icbm4fabric.client.init;

import dev.sebastianb.icbm4fabric.client.Icbm4fabricClient;
import dev.sebastianb.icbm4fabric.client.model.entity.rocket.TaterRocketModel;
import dev.sebastianb.icbm4fabric.client.renderer.entity.rocket.TaterRocketRenderer;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.impl.client.renderer.registry.EntityModelLayerImpl;

@SuppressWarnings("all") // like every good programmer
public class ModClientEntityRegister {
    public static void register() {
        EntityRendererRegistry.INSTANCE.register(ModEntityTypes.TATER_ROCKET, (context)
                -> new TaterRocketRenderer(context));

        EntityModelLayerImpl.PROVIDERS.put(Icbm4fabricClient.TATER_LAYER, TaterRocketModel::getTextureModelData);
    }
}
