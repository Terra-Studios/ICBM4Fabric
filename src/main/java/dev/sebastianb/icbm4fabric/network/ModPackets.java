package dev.sebastianb.icbm4fabric.network;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.logging.Level;

public class ModPackets {

    public static void register() {

        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.SUMMON_MISSILE, ((server, player, handler, buf, responseSender) -> {
            ICBM4Fabric.LOGGER.log(Level.ALL, "HI");

            BlockPos missileBase = buf.readBlockPos();
            ServerWorld serverWorld = player.getWorld();
            server.execute((() -> {
                AbstractMissileProjectile missileEntity = ModEntityTypes.Missiles.TATER.getType().create(serverWorld);
                missileEntity.updatePosition(missileBase.getX() + 0.5, missileBase.getY() + 0.2, missileBase.getZ() + 0.5);
                serverWorld.spawnEntity(missileEntity);
            }));
        }));

        ServerPlayNetworking.registerGlobalReceiver(Constants.Packets.UPDATE_LAUNCH_SCREEN_FIELD, (((server, player, handler, buf, responseSender) -> {

            BlockPos target = buf.readBlockPos();
            BlockPos pos = buf.readBlockPos();
            ServerWorld serverWorld = player.getWorld();
            server.execute(() -> {
                if (serverWorld.getBlockEntity(pos) instanceof GenericMissileLauncherEntity) {
                    GenericMissileLauncherEntity blockEntity = (GenericMissileLauncherEntity) serverWorld.getBlockEntity(pos);

                    blockEntity.setTarget(target);
                }
            });
        })));
    }
}
