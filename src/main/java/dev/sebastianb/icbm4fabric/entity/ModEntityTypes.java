package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntityTypes {

    public static final EntityType<TaterRocketEntity> TATER_ROCKET
            = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(Constants.MOD_ID, Constants.Entity.TATER_MISSILE),
                FabricEntityTypeBuilder.create(SpawnGroup.MISC, TaterRocketEntity::new)
                        .dimensions(EntityDimensions.fixed(0.75f, 2.5f)).build()

    );


    public static void register() {
        FabricDefaultAttributeRegistry.register(TATER_ROCKET, TaterRocketEntity.createMobAttributes());
    }
}
