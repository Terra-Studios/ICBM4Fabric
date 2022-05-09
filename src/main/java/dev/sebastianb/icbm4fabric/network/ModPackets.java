package dev.sebastianb.icbm4fabric.network;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

public class ModPackets {

    public static void register() {

        // maybe not needed?
        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.SUMMON_MISSILE, ((server, player, handler, buf, responseSender) -> {

            BlockPos missileBase = buf.readBlockPos();
            ServerWorld serverWorld = player.getWorld();
            server.execute((() -> {
                AbstractMissileProjectile missileEntity = ModEntityTypes.Missiles.TATER.getType().create(serverWorld);
                missileEntity.updatePosition(missileBase.getX() + 0.5, missileBase.getY() + 0.2, missileBase.getZ() + 0.5);
                serverWorld.spawnEntity(missileEntity);
            }));
        }));

        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.NEW_LAUNCH_CORDS, (server, player, handler, buf, responseSender) -> {

            BlockPos target = buf.readBlockPos();
            BlockPos pos = buf.readBlockPos();
            // boolean hasMissile = buf.readBoolean();
            ServerWorld serverWorld = player.getWorld();
            server.execute(() -> {
                if (serverWorld.getBlockEntity(pos) instanceof GenericMissileLauncherEntity blockEntity) {
                    blockEntity.setTarget(target);
                    // blockEntity.setHasMissile(hasMissile);
                }
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.LAUNCH_MISSILE, (server, player, handler, buf, responseSender) -> { // when missile should launch
            BlockPos pos = buf.readBlockPos();
            ServerWorld serverWorld = player.getWorld();
            boolean hasMissile = buf.readBoolean();
            server.execute(() -> {
                if (serverWorld.getBlockEntity(pos) instanceof GenericMissileLauncherEntity blockEntity) {
                    blockEntity.launchMissile(); // really logic and code goes here
                }
            });
        });
    }
}
