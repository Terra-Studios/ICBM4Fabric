package dev.sebastianb.icbm4fabric.block.launcher;

import org.jetbrains.annotations.Nullable;

import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class GenericRocketLauncherEntity extends BlockEntity implements NamedScreenHandlerFactory {
    public GenericRocketLauncherEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.ROCKET_LAUNCHER, pos, state);
    }

    @Override
    public Text getDisplayName() {
        return LiteralText.EMPTY;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new LaunchScreenHandler(syncId, inv);
    }
}
