package dev.sebastianb.icbm4fabric.client.renderer.entity.rocket;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.model.entity.rocket.TaterRocketModel;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.PhantomEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class TaterRocketRenderer extends MobEntityRenderer<TaterRocketEntity, TaterRocketModel> {


    public TaterRocketRenderer(EntityRendererFactory.Context context) {
        super(context, new TaterRocketModel(), 0.0f);
    }




    @Override
    public Identifier getTexture(TaterRocketEntity entity) {
        return new Identifier(Constants.MOD_ID, "textures/entity/missile/tater_missile.png");
    }




    // pain
    @Override
    public void render(TaterRocketEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {

        float radianYaw = (float) Math.toRadians(entity.getYaw()); // it was just the `yaw` variable b4
        float radianPitch = (float) Math.toRadians(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())); // it was just the `pitch` variable b4

        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(radianYaw));
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(radianPitch));

        super.render(entity, tickDelta, yaw, matrices, vertexConsumerProvider, light);

    }
}
