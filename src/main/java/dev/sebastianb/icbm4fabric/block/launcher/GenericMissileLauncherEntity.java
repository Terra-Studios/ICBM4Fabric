package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
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

public class GenericMissileLauncherEntity extends BlockEntity implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {

    AbstractMissileProjectile missile;

    BlockPos target;

    public GenericMissileLauncherEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        int x = nbt.getInt("targetX");
        int y = nbt.getInt("targetY");
        int z = nbt.getInt("targetZ");

        setTarget(new BlockPos(x, y, z));

        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("targetX", target.getX());
        nbt.putInt("targetY", target.getY());
        nbt.putInt("targetZ", target.getZ());

        super.writeNbt(nbt);
    }

    public GenericMissileLauncherEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MISSILE_LAUNCHER, pos, state);

        target = BlockPos.ORIGIN;
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

    public void setMissile(AbstractMissileProjectile missile) {
        this.missile = missile;
    }

    public AbstractMissileProjectile getMissile() {
        return missile;
    }

    public void setTarget(BlockPos pos) {
        target = pos;

        markDirty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(getPos());

        buf.writeBlockPos(target);
    }
}
