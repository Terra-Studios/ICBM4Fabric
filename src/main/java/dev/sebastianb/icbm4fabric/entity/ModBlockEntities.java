package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.block.launcher.GenericRocketLauncherEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {



    // TODO: Port to 1.17! Block entities broken

    public static final BlockEntityType<GenericRocketLauncherEntity> GENERIC_ROCKET_LAUNCHER =
            FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, Blocks.BARRIER).build(null);

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER1_ROCKET_LAUNCHER =
            FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER1_ROCKET_LAUNCHER).build(null);

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER2_ROCKET_LAUNCHER =
            FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER2_ROCKET_LAUNCHER).build(null);

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER3_ROCKET_LAUNCHER =
            FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER3_ROCKET_LAUNCHER).build(null);



    public static void register() {

    }




}
