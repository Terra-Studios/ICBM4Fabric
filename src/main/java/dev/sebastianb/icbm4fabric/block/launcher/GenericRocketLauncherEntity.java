package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.client.gui.LaunchScreen;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
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
        return null;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new LaunchScreenHandler(syncId, inv);
    }



}
