package dev.sebastianb.icbm4fabric.client.renderer.entity.missile;

import dev.sebastianb.icbm4fabric.client.model.entity.missile.MissileModel;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;

abstract class MissileRenderer<T extends AbstractMissileProjectile> extends EntityRenderer<T> {

    private MissileModel<T> model;

    public MissileRenderer(Context context, MissileModel<T> model) {
        super(context);
        this.model = model;
    }

    @Override
    public void render(T entity, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
        VertexConsumer buffer = vertices.getBuffer(RenderLayer.getEntitySolid(getTexture(entity)));
        model.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        super.render(entity, yaw, delta, matrices, vertices, light);
    }
}
