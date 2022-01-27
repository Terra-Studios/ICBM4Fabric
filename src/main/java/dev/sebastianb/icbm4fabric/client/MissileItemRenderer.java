package dev.sebastianb.icbm4fabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import dev.sebastianb.icbm4fabric.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public class MissileItemRenderer {

    public static void register() {
        BuiltinItemRendererRegistry.DynamicItemRenderer itemRenderer = MissileItemRenderer::render;
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TATER_MISSILE, itemRenderer);
    }

    private static void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        if (mode == ModelTransformation.Mode.GUI) {

        }
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();

        Entity entity = new TaterRocketEntity(ModEntityTypes.TATER_ROCKET, MinecraftClient.getInstance().world);
        matrixStack.push();
        RenderSystem.runAsFancy(() -> {
            entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, vertexConsumerProvider,
                    LightmapTextureManager.MAX_LIGHT_COORDINATE);
        });
        matrixStack.pop();
    }

}
