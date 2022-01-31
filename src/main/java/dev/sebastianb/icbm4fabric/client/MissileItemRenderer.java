package dev.sebastianb.icbm4fabric.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.rocket.AbstractRocketProjectile;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import dev.sebastianb.icbm4fabric.item.ModItems;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.BlockEntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;

public class MissileItemRenderer {

    public static void register() {
        BuiltinItemRendererRegistry.DynamicItemRenderer itemRenderer = MissileItemRenderer::render;
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.TATER_MISSILE, itemRenderer);
    }

    private static void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        AbstractRocketProjectile entity = new TaterRocketEntity(ModEntityTypes.TATER_ROCKET, MinecraftClient.getInstance().world);
        entity.setYaw(0); // used so the entity doesn't freak out when rendering

        switch (mode) {
            case THIRD_PERSON_RIGHT_HAND:
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(54));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(270));
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-180));

                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, -0.17D, 0.2D, 0.38D, 0.0F, 1.0F, matrixStack, vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
                break;
            case GROUND:
                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, 0.5, 0, 0.5, 0.0F, 1.0F, matrixStack, vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
                break;
            default:
                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack, vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
        }
    }

}


