package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class GenericRocketLauncherEntity extends BlockEntity implements NamedScreenHandlerFactory {



    public GenericRocketLauncherEntity(BlockEntityType<?> type) {
        super(type);
    }

    public GenericRocketLauncherEntity() {
        super(ModBlockEntities.GENERIC_ROCKET_LAUNCHER);
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
