package dev.sebastianb.icbm4fabric.client.init;

import dev.sebastianb.icbm4fabric.client.Icbm4fabricClient;
import dev.sebastianb.icbm4fabric.client.model.entity.missile.TaterMissileModel;
import dev.sebastianb.icbm4fabric.client.renderer.entity.missile.TaterMissileRenderer;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class ModClientEntityRegister {
    public static void register() {
        EntityRendererRegistry.register(ModEntityTypes.TATER_MISSILE, TaterMissileRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(Icbm4fabricClient.TATER_LAYER, TaterMissileModel::getTextureModelData);
    }
}
