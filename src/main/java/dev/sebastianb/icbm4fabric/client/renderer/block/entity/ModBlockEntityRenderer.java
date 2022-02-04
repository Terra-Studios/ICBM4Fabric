package dev.sebastianb.icbm4fabric.client.renderer.block.entity;

import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;

public class ModBlockEntityRenderer {
    public static void register() {
        BlockEntityRendererRegistry.register(ModBlockEntities.MISSILE_LAUNCHER, LauncherBlockEntityRenderer::new);
    }
}
