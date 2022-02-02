package dev.sebastianb.icbm4fabric.client;

import com.mojang.blaze3d.systems.RenderSystem;

import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class MissileItemRenderer implements DynamicItemRenderer {

    public MissileItemRenderer() {
        
    }

    @Override
    @SuppressWarnings("resource")
    public void render(ItemStack itemStack, ModelTransformation.Mode mode, MatrixStack matrixStack,
            VertexConsumerProvider vertexConsumerProvider, int i, int i1) {
        EntityRenderDispatcher entityRenderDispatcher = MinecraftClient.getInstance().getEntityRenderDispatcher();
        AbstractMissileProjectile entity = new TaterMissileEntity(ModEntityTypes.Missiles.TATER.getType(),
                MinecraftClient.getInstance().world);
        entity.setYaw(270); // used to adjust the missile in GUI to fix what atakku did
        entity.setPitch(90); // yes, I know this is a hack

        switch (mode) {
            case THIRD_PERSON_RIGHT_HAND:
                matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(54));
                matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(270));
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(-180));

                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, -0.17D, 0.2D, 0.38D, 0.0F, 1.0F, matrixStack,
                            vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
                break;
            case GROUND:
                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, 0.5, 0, 0.5, 0.0F, 1.0F, matrixStack, vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
                break;
            case FIRST_PERSON_RIGHT_HAND:
                matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90));
                matrixStack.scale(1.6F,1.6F,1.6F);
                RenderSystem.runAsFancy(() -> {
                entityRenderDispatcher.render(entity, 1.8, -0.6, -0.2D, 0.0F, 1.0F, matrixStack,
                        vertexConsumerProvider,
                        LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
                break;
            default:
                RenderSystem.runAsFancy(() -> {
                    entityRenderDispatcher.render(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixStack,
                            vertexConsumerProvider,
                            LightmapTextureManager.MAX_LIGHT_COORDINATE);
                });
        }
    }

}
