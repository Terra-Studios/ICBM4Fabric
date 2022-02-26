package dev.sebastianb.icbm4fabric.item.missile;

import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncher;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class MissileItem extends Item {

    public MissileItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) { // not entirely sure if this even gets called when the launcher is clicked 
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        if (player != null && world != null) {

            if (world.getBlockState(context.getBlockPos()).getBlock() instanceof GenericMissileLauncher) {
                BlockEntity blockEntity = world.getBlockEntity(context.getBlockPos());

                if (blockEntity != null) {
                    if (blockEntity instanceof GenericMissileLauncherEntity) {
                        GenericMissileLauncherEntity launcherEntity = (GenericMissileLauncherEntity) blockEntity;

                        if (!launcherEntity.hasMissile()) {
                            context.getStack().decrement(1);
                            launcherEntity.setMissile(context.getStack());
                            return ActionResult.CONSUME;
                        }
                    }
                }
            }
        }

        return super.useOnBlock(context);
    }

    public AbstractMissileProjectile getMissile(World world) {
        return new TaterMissileEntity(ModEntityTypes.Missiles.TATER.getType(), world);
    }
}
