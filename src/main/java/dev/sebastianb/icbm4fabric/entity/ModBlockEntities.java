package dev.sebastianb.icbm4fabric.entity;

import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.block.ModBlocks;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import net.minecraft.block.entity.BlockEntityType;

public class ModBlockEntities {
  public static final BlockEntityType<GenericMissileLauncherEntity> MISSILE_LAUNCHER = ICBM4Fabric.REGISTRY.blockEntity(GenericMissileLauncherEntity::new, "missile_launcher", ModBlocks.MissileLaunchers.values());

  public static void register() {

  }
}
