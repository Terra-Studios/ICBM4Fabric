package dev.sebastianb.icbm4fabric.network;

import java.util.logging.Level;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

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
    }
}
