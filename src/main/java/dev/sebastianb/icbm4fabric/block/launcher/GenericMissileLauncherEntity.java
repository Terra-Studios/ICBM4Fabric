package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.client.gui.LaunchScreenHandler;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.entity.missile.TaterMissileEntity;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

public class GenericMissileLauncherEntity extends BlockEntity implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory {

    BlockPos target;

    private ItemStack missileItemStack = ItemStack.EMPTY;

    Collection<ServerPlayerEntity> lastPlayersTracking;

    @Override
    public void readNbt(NbtCompound nbt) {
        // read target and if we have a missile from nbt
        int x = nbt.getInt("targetX");
        int y = nbt.getInt("targetY");
        int z = nbt.getInt("targetZ");

        target = new BlockPos(x, y, z);

        missileItemStack = ItemStack.fromNbt(nbt.getCompound("missileItemStack"));

        super.readNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        // write target and if we have missile to nbt
        nbt.putInt("targetX", target.getX());
        nbt.putInt("targetY", target.getY());
        nbt.putInt("targetZ", target.getZ());

        nbt.put("missileItemStack", missileItemStack.writeNbt(new NbtCompound()));

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
        return new LaunchScreenHandler(syncId, inv); // create screen handler
    }

    public void setMissile(ItemStack missile) {
        this.missileItemStack = missile; // set the missile
        markDirty(); // mark dirty so writeNbt() gets called
    }

    public ItemStack getMissile() {
        return this.missileItemStack;
    }

    public void setTarget(BlockPos pos) {
        this.target = pos;
        markDirty(); // mark dirty so writeNbt() gets called
    }

    public boolean hasMissile() {
        return !missileItemStack.isEmpty();
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(getPos()); // buf in constructor of LaunchScreenHandler gets written here
        buf.writeBlockPos(target);
        buf.writeBoolean(hasMissile());
    }

    public void launchMissile() {
        if (hasMissile()) { // make sure we have a missile to launch

            AbstractMissileProjectile missileEntity = new TaterMissileEntity(ModEntityTypes.Missiles.TATER.getType(), world); // create the missile
            missileEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5); // set position of missile

            world.spawnEntity(missileEntity);
            missileEntity.setFinalBlockPos(target); // set target

            missileEntity.setStage(LaunchStage.LIT); // light the rocket
        }
    }

    public static <T extends BlockEntity> void tick(World world, BlockPos pos, BlockState blockState, T t) {
        if (!world.isClient) {
            if (!(t instanceof GenericMissileLauncherEntity)) return;

            GenericMissileLauncherEntity be = (GenericMissileLauncherEntity) world.getBlockEntity(pos);

            var playerList = PlayerLookup.tracking(be);

            Collection<ServerPlayerEntity> playersToUpdate = new ArrayList<>();

            if (be.lastPlayersTracking == null) {
                playersToUpdate.addAll(playerList);
            } else {
                for (ServerPlayerEntity player : playerList) {
                    if (!be.lastPlayersTracking.contains(player)) {
                        playersToUpdate.add(player);
                    }
                }
            }

            if (playersToUpdate.isEmpty()) return;

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeItemStack(be.getMissile());
            buf.writeBlockPos(pos);

            for (ServerPlayerEntity player : playersToUpdate) {
                ServerPlayNetworking.send(player, Constants.Packets.LAUNCHER_STATUS, buf);
            }

            be.lastPlayersTracking = playerList;
        }
    }


}
