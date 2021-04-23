package dev.sebastianb.icbm4fabric.client.renderer.entity.rocket;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.model.entity.rocket.TaterRocketModel;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class TaterRocketRenderer extends MobEntityRenderer<TaterRocketEntity, TaterRocketModel> {


    public TaterRocketRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new TaterRocketModel(), 0.0f);
    }

    @Override
    public Identifier getTexture(TaterRocketEntity entity) {
        return new Identifier(Constants.MOD_ID, "textures/entity/missile/tater_missile.png");
    }


    // pain
    @Override
    public void render(TaterRocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {

        float radianYaw = (float) Math.toRadians(entity.yaw);
        float radianPitch = (float) Math.toRadians(MathHelper.lerp(tickDelta, entity.prevPitch, entity.pitch));

        matrices.multiply(Vector3f.POSITIVE_Y.getRadialQuaternion(radianYaw));
        matrices.multiply(Vector3f.POSITIVE_X.getRadialQuaternion(radianPitch));

        super.render(entity, tickDelta, yaw, matrices, vertexConsumerProvider, light);

    }
}
