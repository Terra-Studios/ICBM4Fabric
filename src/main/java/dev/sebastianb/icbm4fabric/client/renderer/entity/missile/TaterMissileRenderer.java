package dev.sebastianb.icbm4fabric.client.renderer.entity.missile;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.client.Icbm4fabricClient;
import dev.sebastianb.icbm4fabric.client.model.entity.missile.TaterMissileModel;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;

public class TaterMissileRenderer extends MissileRenderer<TaterMissileEntity> {
    public TaterMissileRenderer(EntityRendererFactory.Context context) {
        super(context, new TaterMissileModel(context.getPart(Icbm4fabricClient.TATER_LAYER)));
    }

    @Override
    public Identifier getTexture(TaterMissileEntity entity) {
        return new Identifier(Constants.MOD_ID, "textures/entity/missile/tater_missile.png");
    }
}
