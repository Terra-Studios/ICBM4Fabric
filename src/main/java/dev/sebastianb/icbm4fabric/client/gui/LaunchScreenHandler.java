package dev.sebastianb.icbm4fabric.client.gui;

import dev.sebastianb.icbm4fabric.registries.ModScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.ScreenHandler;

public class LaunchScreenHandler extends ScreenHandler {


    public LaunchScreenHandler(int syncID, Inventory inventory) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncID);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }
}
