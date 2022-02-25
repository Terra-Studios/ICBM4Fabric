package dev.sebastianb.icbm4fabric.network;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.block.launcher.GenericMissileLauncherEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public class ClientPackets {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(Constants.Packets.LAUNCHER_STATUS, ((client, handler, buf, responseSender) -> {
            ItemStack missile = buf.readItemStack();
            BlockPos pos = buf.readBlockPos();

            client.execute(() -> {
                BlockEntity be = client.world.getBlockEntity(pos);

                if (!(be instanceof GenericMissileLauncherEntity)) return;

                ((GenericMissileLauncherEntity) be).setMissile(missile);
            });
        }));
    }
}
