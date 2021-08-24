package dev.sebastianb.icbm4fabric.client.gui;

import dev.sebastianb.icbm4fabric.client.gui.info.BlockStringGUIPos;
import dev.sebastianb.icbm4fabric.registries.ModScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaunchScreenHandler extends ScreenHandler {

    private BlockStringGUIPos guiPositionHolder = new BlockStringGUIPos();

    private BlockPos missileFinalLocation;

    public LaunchScreenHandler(int syncId, Inventory inventory, PacketByteBuf buf) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncId);
    }

    public LaunchScreenHandler(int syncId, World world, BlockPos pos) {
        super(ModScreenHandlerRegistry.LAUNCHER_SCREEN, syncId);
        ScreenHandlerContext context = ScreenHandlerContext.create(world, pos);
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    public void setMissileFinalLocation(BlockPos pos) {
        guiPositionHolder = new BlockStringGUIPos(pos.getX(), pos.getY(), pos.getZ());
    }

    public BlockStringGUIPos getGuiPositionHolder() {
        return guiPositionHolder;
    }


}
