package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.block.launcher.GenericRocketLauncherEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModBlockEntities {

    public static final BlockEntityType<GenericRocketLauncherEntity> GENERIC_ROCKET_LAUNCHER =
            register(FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, Blocks.BARRIER).build(null), Blocks.BARRIER.getLootTableId());

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER1_ROCKET_LAUNCHER =
            register(FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER1_ROCKET_LAUNCHER).build(null),
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER1_MISSILE_LAUNCHER));

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER2_ROCKET_LAUNCHER =
            register(FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER2_ROCKET_LAUNCHER).build(null),
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER2_MISSILE_LAUNCHER));

    public static final BlockEntityType<GenericRocketLauncherEntity> TIER3_ROCKET_LAUNCHER =
            register(FabricBlockEntityTypeBuilder.create(
                    GenericRocketLauncherEntity::new, ModBlocks.TIER3_ROCKET_LAUNCHER).build(null),
                    new Identifier(Constants.MOD_ID, Constants.Blocks.TIER3_MISSILE_LAUNCHER));



    private static <T extends BlockEntity> BlockEntityType<T> register(BlockEntityType<T> build, Identifier identifier) {
        Registry.register(Registry.BLOCK_ENTITY_TYPE, identifier, build);
        return build;
    }


    public static void register() {

    }


}
