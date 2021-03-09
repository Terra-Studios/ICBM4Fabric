package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class GenericRocketLauncher extends Block implements BlockEntityProvider {

    private Constants.LauncherType launcherType = Constants.LauncherType.NOT_AVAILABLE;

    public GenericRocketLauncher(Settings settings, Constants.LauncherType type) {
        super(settings);
        this.launcherType = type;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        switch (launcherType) {
            case TIER1:
                System.out.println("t1");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER1_ROCKET_LAUNCHER);
            case TIER2:
                System.out.println("t2");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER2_ROCKET_LAUNCHER);
            case TIER3:
                System.out.println("t3");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER3_ROCKET_LAUNCHER);
            default:
                return null; // TODO: fix with a failsafe later lol
        }
    }
}
