package dev.sebastianb.icbm4fabric.client.renderer.entity.missile;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.Icbm4fabricClient;
import dev.sebastianb.icbm4fabric.client.model.entity.missile.TaterMissileModel;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class TaterMissileRenderer extends MobEntityRenderer<TaterMissileEntity, TaterMissileModel> {


    public TaterMissileRenderer(EntityRendererFactory.Context context) {
        super(context, new TaterMissileModel(context.getPart(Icbm4fabricClient.TATER_LAYER)), 0.0f);
    }

    @Override
    public Identifier getTexture(TaterMissileEntity entity) {
        return new Identifier(Constants.MOD_ID, "textures/entity/missile/tater_missile.png");
    }

    // pain
    @Override
    public void render(TaterMissileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int light) {
//        float radianYaw = (float) Math.toRadians(entity.getYaw());
//        float radianPitch = (float) Math.toRadians(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())); // it was just the `pitch` variable b4

        float radianYaw = (float) Math.toRadians(MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw()));
        float radianPitch = (float) Math.toRadians(MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch())); // it was just the `pitch` variable b4

        // System.out.println("radianYaw: " + radianYaw + " radianPitch: " + radianPitch);

        // TODO: figure out why tf this goes "crazy" and spins like a madman
        // ^ this is figured out inside the item now but on pathes, it goes crazy still

        matrices.multiply(Vec3f.POSITIVE_Y.getRadialQuaternion(radianYaw));
        matrices.multiply(Vec3f.POSITIVE_X.getRadialQuaternion(radianPitch));

        super.render(entity, tickDelta, yaw, matrices, vertexConsumerProvider, light);
    }
}
