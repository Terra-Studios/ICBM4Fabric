package dev.sebastianb.icbm4fabric.client.init;

import dev.sebastianb.icbm4fabric.client.Icbm4fabricClient;
import dev.sebastianb.icbm4fabric.client.model.entity.missile.TaterMissileModel;
import dev.sebastianb.icbm4fabric.client.renderer.entity.missile.TaterMissileRenderer;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.entity.EntityType;

public class ModClientEntityRegister {
    public static void register() {
        EntityRendererRegistry.register((EntityType) ModEntityTypes.Missiles.TATER.getType(), TaterMissileRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(Icbm4fabricClient.TATER_LAYER, TaterMissileModel::getTextureModelData);
    }
}
