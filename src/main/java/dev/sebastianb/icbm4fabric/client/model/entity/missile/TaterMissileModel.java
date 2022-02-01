package dev.sebastianb.icbm4fabric.client.model.entity.missile;

import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class TaterMissileModel extends MissileModel<TaterMissileEntity> {

    private final ModelPart root;

    public TaterMissileModel(ModelPart root) {
        this.root = root;
        this.root.setPivot(0.0F, 24.0F, 0.0F);
    }

    public static TexturedModelData getTextureModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-2.0F, -30.0F, -2.0F, 4.0F, 30.0F, 4.0F),
                ModelTransform.NONE);

        ModelPartData walls = modelPartData.addChild("walls", ModelPartBuilder.create(),
                ModelTransform.NONE);

        walls.addChild("wall4_r1", ModelPartBuilder.create().uv(24, 15)
                .cuboid(-2.9F, -8.3F, -2.0F, 1.0F, 27.0F, 4.0F),
                ModelTransform.pivot(0.0F, -19.0F, 0.0F));

        walls.addChild("wall3_r1", ModelPartBuilder.create().uv(34, 15)
                .cuboid(-2.9F, -8.3F, -2.0F, 1.0F, 27.0F, 4.0F),
                ModelTransform.of(0.0F, -19.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        walls.addChild("wall2_r1", ModelPartBuilder.create().uv(44, 15)
                .cuboid(-2.9F, -8.3F, -2.0F, 1.0F, 27.0F, 4.0F),
                ModelTransform.of(0.0F, -19.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));

        walls.addChild("wall1_r1", ModelPartBuilder.create().uv(54, 15)
                .cuboid(-2.9F, -8.3F, -2.0F, 1.0F, 27.0F, 4.0F),
                ModelTransform.of(0.0F, -19.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData fins = modelPartData.addChild("fins", ModelPartBuilder.create(),
                ModelTransform.pivot(4.6963F, -6.275F, 2.1213F));

        ModelPartData fin1 = fins.addChild("fin1", ModelPartBuilder.create(),
                ModelTransform.pivot(-4.6963F, 1.275F, -2.1213F));

        fin1.addChild("top_r1", ModelPartBuilder.create().uv(54, 46)
                .cuboid(-4.55F, -8.15F, -0.5F, 4.0F, 9.0F, 1.0F),
                ModelTransform.of(4.2426F, -3.0F, 4.2426F, 0.4859F, -0.6389F, -0.7287F));

        fin1.addChild("bottom_r1", ModelPartBuilder.create().uv(54, 56)
                .cuboid(2.0F, -2.0F, -0.5F, 4.0F, 7.0F, 1.0F),
                ModelTransform.rotation(0.0F, -0.7854F, 0.0F));

        ModelPartData fin2 = fins.addChild("fin2", ModelPartBuilder.create(),
                ModelTransform.of(-4.6963F, 1.275F, -2.1213F, 0.0F, -1.5708F, 0.0F)); // works

        fin2.addChild("top_r2", ModelPartBuilder.create().uv(44, 46)
                .cuboid(-4.55F, -8.15F, -0.5F, 4.0F, 9.0F, 1.0F),
                ModelTransform.of(4.2426F, -3.0F, 4.2426F, 0.4859F, -0.6389F, -0.7287F));

        fin2.addChild("bottom_r2", ModelPartBuilder.create().uv(44, 56)
                .cuboid(2.0F, -2.0F, -0.5F, 4.0F, 7.0F, 1.0F),
                ModelTransform.rotation(0.0F, -0.7854F, 0.0F));

        ModelPartData fin3 = fins.addChild("fin3", ModelPartBuilder.create(),
                ModelTransform.of(-4.6963F, 1.275F, -2.1213F, -3.1416F, 0.0F, 3.1416F)); // works

        fin3.addChild("top_r3", ModelPartBuilder.create().uv(34, 46)
                .cuboid(-4.55F, -8.15F, -0.5F, 4.0F, 9.0F, 1.0F),
                ModelTransform.of(4.2426F, -3.0F, 4.2426F, 0.4859F, -0.6389F, -0.7287F));

        fin3.addChild("bottom_r3", ModelPartBuilder.create().uv(34, 56)
                .cuboid(2.0F, -2.0F, -0.5F, 4.0F, 7.0F, 1.0F),
                ModelTransform.rotation(0.0F, -0.7854F, 0.0F));

        ModelPartData fin4 = fins.addChild("fin4", ModelPartBuilder.create(),
                ModelTransform.of(-4.6963F, 1.275F, -2.1213F, 0.0F, 1.5708F, 0.0F)); // works

        fin4.addChild("top_r4", ModelPartBuilder.create().uv(24, 46)
                .cuboid(-4.5F, -8.15F, -0.5F, 4.0F, 9.0F, 1.0F),
                ModelTransform.of(4.2426F, -3.0F, 4.2426F, 0.4859F, -0.6389F, -0.7287F));

        fin4.addChild("bottom_r4", ModelPartBuilder.create().uv(34, 56)
                .cuboid(2.0F, -2.0F, -0.5F, 4.0F, 7.0F, 1.0F),
                ModelTransform.rotation(0.0F, -0.7854F, 0.0F));

        ModelPartData nose = modelPartData.addChild("nose", ModelPartBuilder.create().uv(48, 0)
                .cuboid(-2.0F, -36.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.NONE);

        nose.addChild("nose_tip", ModelPartBuilder.create().uv(44, 4)
                .cuboid(-0.5F, -41.0F, -0.5F, 1.0F, 5.0F, 1.0F), ModelTransform.NONE);

        nose.addChild("base4_r1", ModelPartBuilder.create().uv(24, 0)
                .cuboid(-2.05F, 0.0F, -2.0F, 1.0F, 3.0F, 4.0F),
                ModelTransform.of(0.0F, -29.5F, 0.0F, 0.0F, 0.0F, 0.3316F));

        nose.addChild("base3_r1", ModelPartBuilder.create().uv(34, 0)
                .cuboid(-2.05F, 0.0F, -2.0F, 1.0F, 3.0F, 4.0F),
                ModelTransform.of(0.0F, -29.5F, 0.0F, 1.5708F, 1.2392F, 1.5708F));

        nose.addChild("base2_r1", ModelPartBuilder.create().uv(24, 7)
                .cuboid(-2.05F, 0.0F, -2.0F, 1.0F, 3.0F, 4.0F),
                ModelTransform.of(0.0F, -29.5F, 0.0F, -3.1416F, 0.0F, 2.81F));

        nose.addChild("base1_r1", ModelPartBuilder.create().uv(34, 7)
                .cuboid(-2.05F, 0.0F, -2.0F, 1.0F, 3.0F, 4.0F),
                ModelTransform.of(0.0F, -29.5F, 0.0F, -1.5708F, -1.2392F, 1.5708F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(TaterMissileEntity entity, float limbAngle, float limbDistance, float animationProgress,
            float headYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green,
            float blue, float alpha) {
        root.render(matrices, vertices, light, overlay);
    }

}
