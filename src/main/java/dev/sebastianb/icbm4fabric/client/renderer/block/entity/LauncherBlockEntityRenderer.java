package dev.sebastianb.icbm4fabric.client.renderer.block.entity;

import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import dev.sebastianb.icbm4fabric.item.ModItems;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class LauncherBlockEntityRenderer implements BlockEntityRenderer {
    ItemStack missile = new ItemStack(ModItems.Missiles.TATER, 1);

    public LauncherBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(BlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();

        matrices.translate(0.5, .75, 0.5);

        if (entity instanceof GenericMissileLauncherEntity) {
            GenericMissileLauncherEntity launcherEntity = (GenericMissileLauncherEntity) entity;

            if (launcherEntity.hasMissile) {
                MinecraftClient.getInstance().getItemRenderer().renderItem(missile, ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
            }

//            System.out.println(launcherEntity.hasMissile);
        }

        matrices.pop();
    }
}
