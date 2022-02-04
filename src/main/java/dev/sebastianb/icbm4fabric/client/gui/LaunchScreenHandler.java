package dev.sebastianb.icbm4fabric.client.gui;

import dev.sebastianb.icbm4fabric.item.ModItems;
import dev.sebastianb.icbm4fabric.registries.ModScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.math.BlockPos;

public class LaunchScreenHandler extends ScreenHandler {

    private BlockPos pos;

    public LaunchScreenHandler(int syncID, Inventory inventory, PacketByteBuf buf) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncID);

        pos = buf.readBlockPos();
    }

    public LaunchScreenHandler(int syncID, Inventory inventory) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncID);

        pos = BlockPos.ORIGIN;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !player.isHolding(ModItems.Missiles.TATER.asItem());
    }

    public BlockPos getPos() {
        return pos;
    }
}
