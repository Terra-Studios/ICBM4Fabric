package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.block.launcher.GenericRocketLauncherEntity;
import net.minecraft.block.entity.BlockEntityType;

public class ModBlockEntities {
  public static final BlockEntityType<GenericRocketLauncherEntity> ROCKET_LAUNCHER = ICBM4Fabric.REGISTRY.blockEntity(GenericRocketLauncherEntity::new, "rocket_launcher", ModBlocks.RocketLaunchers.values());

  public static void register() {

  }

}
