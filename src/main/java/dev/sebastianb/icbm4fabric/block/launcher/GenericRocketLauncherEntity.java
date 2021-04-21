package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.client.gui.LauncherScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
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
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new LauncherScreenHandler(syncId, inv, player.inventory); // player.inv is "this"
    }
}
