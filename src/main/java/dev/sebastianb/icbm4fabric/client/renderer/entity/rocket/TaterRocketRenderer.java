package dev.sebastianb.icbm4fabric.client.renderer.entity.rocket;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.model.entity.rocket.TaterRocketModel;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class TaterRocketRenderer extends MobEntityRenderer<TaterRocketEntity, TaterRocketModel> {


    public TaterRocketRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new TaterRocketModel(), 0.5f);
    }

    @Override
    public Identifier getTexture(TaterRocketEntity entity) {
        return new Identifier(Constants.MOD_ID, "textures/entity/missile/tater_missile.png");
    }
}
