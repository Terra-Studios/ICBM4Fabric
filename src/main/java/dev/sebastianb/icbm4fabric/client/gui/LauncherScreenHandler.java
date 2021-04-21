package dev.sebastianb.icbm4fabric.client.gui;

import dev.sebastianb.icbm4fabric.init.ModScreenRegister;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.ScreenHandler;

public class LauncherScreenHandler extends ScreenHandler {

    private final Inventory inventory;


    public LauncherScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(9));
    }

    public LauncherScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenRegister.LAUNCHER_SCREEN_HANDLER_SCREEN_HANDLER, syncId);
        this.inventory = inventory;

    }


    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
