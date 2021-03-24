package dev.sebastianb.icbm4fabric.client.renderer.entity.rocket;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.model.entity.rocket.TaterRocketModel;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
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

    @Override
    protected void setupTransforms(TaterRocketEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        matrices.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(MathHelper.lerp(bodyYaw, entity.prevYaw, entity.yaw) - 90.0F));
        matrices.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(MathHelper.lerp(bodyYaw, entity.prevPitch, entity.pitch)));

    }
}
