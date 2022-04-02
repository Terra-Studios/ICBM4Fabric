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
    private BlockPos target;
    private boolean hasMissile;

    public LaunchScreenHandler(int syncID, Inventory inventory, PacketByteBuf buf) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncID); // buf writen in GenericMissileLauncherEnitty#writeScreenOpeningData

        pos = buf.readBlockPos();
        target = buf.readBlockPos();
        hasMissile = buf.readBoolean();
    }

    public LaunchScreenHandler(int syncID, Inventory inventory) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncID);
        pos = BlockPos.ORIGIN;
        target = BlockPos.ORIGIN;
        hasMissile = false;
        
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return !player.isHolding(ModItems.Missiles.TATER.asItem());
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockPos getTarget() {
        return target;
    }

    public boolean hasMissile() {
        return hasMissile;
    }

    public void setHasMissile(boolean hasMissile) {
        this.hasMissile = hasMissile;
    }
}
