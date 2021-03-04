package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.block.launcher.GenericRocketLauncherEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModEntities {



    public static final BlockEntityType<GenericRocketLauncherEntity> GENERIC_ROCKET_LAUNCHER =
            Registry.register(Registry.BLOCK_ENTITY_TYPE,
                    new Identifier("minecraft", "barrier"),
                    BlockEntityType.Builder.create(GenericRocketLauncherEntity::new, Blocks.BARRIER).build(null));

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER1_ROCKET_LAUNCHER =
            Registry.register(Registry.BLOCK_ENTITY_TYPE,
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER_1_MISSILE_LAUNCHER),
                    BlockEntityType.Builder.create(GenericRocketLauncherEntity::new, ModBlocks.TIER1_ROCKET_LAUNCHER).build(null));

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER2_ROCKET_LAUNCHER =
            Registry.register(Registry.BLOCK_ENTITY_TYPE,
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER2_MISSILE_LAUNCHER),
                    BlockEntityType.Builder.create(GenericRocketLauncherEntity::new, ModBlocks.TIER2_ROCKET_LAUNCHER).build(null));

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER3_ROCKET_LAUNCHER =
            Registry.register(Registry.BLOCK_ENTITY_TYPE,
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER3_MISSILE_LAUNCHER),
                    BlockEntityType.Builder.create(GenericRocketLauncherEntity::new, ModBlocks.TIER3_ROCKET_LAUNCHER).build(null));


    public static void register() {

    }



}
