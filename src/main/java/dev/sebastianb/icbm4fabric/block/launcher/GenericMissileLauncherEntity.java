package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.Nullable;

public class GenericMissileLauncherEntity extends BlockEntity implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {

    private AbstractMissileProjectile missile;

    private int missileId;

    public boolean hasMissile;

    BlockPos target;

    @Override
    public void readNbt(NbtCompound nbt) {
        int x = nbt.getInt("targetX");
        int y = nbt.getInt("targetY");
        int z = nbt.getInt("targetZ");

        target = new BlockPos(x, y, z);

        hasMissile = nbt.getBoolean("hasMissile");

        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("targetX", target.getX());
        nbt.putInt("targetY", target.getY());
        nbt.putInt("targetZ", target.getZ());

        nbt.putBoolean("hasMissile", hasMissile);

        super.writeNbt(nbt);
    }

    public GenericMissileLauncherEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.MISSILE_LAUNCHER, pos, state);

        target = new BlockPos(0, 60, 0); // set default target on screen init. TODO: maybe have a config option for this later?
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

        markDirty();
    }

    public AbstractMissileProjectile getMissile() {

        return this.missile;
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

    public void launchMissile() {
        if (hasMissile) { // make sure we have a missile to launch
            hasMissile = false;

            AbstractMissileProjectile missileEntity = new TaterMissileEntity(ModEntityTypes.Missiles.TATER.getType(), world); // create the missile
            missileEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5); // set position of missile

            world.spawnEntity(missileEntity);
            missileEntity.setFinalBlockPos(target); // set target

            missileEntity.setStage(LaunchStage.LIT); // light the rocket
        }
    }
}
