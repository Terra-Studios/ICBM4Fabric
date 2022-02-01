package dev.sebastianb.icbm4fabric.client.model.entity.missile;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.client.render.entity.model.EntityModel;

// Abstract for now because tater implements everything
abstract public class MissileModel<T extends AbstractMissileProjectile> extends EntityModel<T>{
    
}
