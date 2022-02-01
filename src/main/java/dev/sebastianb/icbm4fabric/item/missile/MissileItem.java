package dev.sebastianb.icbm4fabric.item.missile;

import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncher;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EndCrystalItem;
import net.minecraft.item.FlintAndSteelItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;

public class MissileItem extends Item {

    public MissileItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        if (player != null && world != null) {

            if (world.getBlockState(context.getBlockPos()).getBlock() instanceof GenericMissileLauncher) {
                context.getStack().decrement(1);
                return ActionResult.CONSUME;
            }

        }

        return super.useOnBlock(context);
    }
}
