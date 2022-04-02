package dev.sebastianb.icbm4fabric.client.renderer.entity.missile;

import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.client.model.entity.missile.MissileModel;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

abstract class MissileRenderer<T extends AbstractMissileProjectile> extends EntityRenderer<T> {

    private MissileModel<T> model;
    EntityRendererFactory.Context ctx;

    public MissileRenderer(Context context, MissileModel<T> taterMissileModel) {
        super(context);
        this.model = taterMissileModel;
        this.ctx = context;
    }

    @Override
    public void render(T entity, float yaw, float delta, MatrixStack matrices, VertexConsumerProvider vertices, int light) {
        boolean isLitOrIdle = entity.getStage() == LaunchStage.LIT || entity.getStage() == LaunchStage.IDLE;

        float pitch = isLitOrIdle ? 90 : MathHelper.lerp(delta, entity.prevPitch, entity.getPitch());
        yaw = isLitOrIdle ? -90 : yaw;

        matrices.push();
        matrices.translate(0, 1.25, 0);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(yaw - 90.0f));
        matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(pitch + 90.f));
        matrices.translate(0, -.25, 0);
        VertexConsumer buffer = vertices.getBuffer(RenderLayer.getEntitySolid(getTexture(entity)));
        model.render(matrices, buffer, light, OverlayTexture.DEFAULT_UV, 1f, 1f, 1f, 1f);
        matrices.pop();
    }
}
