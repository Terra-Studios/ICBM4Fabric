package dev.sebastianb.icbm4fabric.entity;

import java.util.Arrays;
import java.util.Locale;

import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EntityType.EntityFactory;
import net.minecraft.entity.SpawnGroup;

public class ModEntityTypes {

    public enum Missiles {
        TATER(TaterMissileEntity::new, 0.75f, 2.5f);

        private final String name;
        private final EntityType<AbstractMissileProjectile> entityType;

        Missiles(EntityFactory<AbstractMissileProjectile> factory, float width, float height) {
            name = this.toString().toLowerCase(Locale.ROOT) + "_missile";
            entityType = ICBM4Fabric.REGISTRY.entityType(FabricEntityTypeBuilder.create(SpawnGroup.MISC, factory)
                            .dimensions(EntityDimensions.fixed(width, height))
                            .forceTrackedVelocityUpdates(true)
                            .trackedUpdateRate(20)
                            .trackRangeBlocks(256)
                    , name);
        }

        public EntityType<AbstractMissileProjectile> getType() {
            return entityType;
        }
    }

    public static void register() {
        // Make sure everything is initialized
        Arrays.stream(Missiles.values()).forEach(v -> System.out.println(v.name));
    }
}
