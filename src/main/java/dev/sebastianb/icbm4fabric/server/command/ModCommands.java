package dev.sebastianb.icbm4fabric.server.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.blast.DesertBlast;
import dev.sebastianb.icbm4fabric.blast.TNTBlast;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.command.TeleportCommand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.explosion.Explosion;

import java.util.Arrays;
import java.util.logging.Level;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {

            //TODO: Rework the command to be any explosion with coord positions
            dispatcher.register(
                    CommandManager.literal("summontestexplosion")
                    .executes(ModCommands::summonExplosion)
            );

        }));

    }

    private static int summonExplosion(CommandContext<ServerCommandSource> commandContext) {
        PlayerEntity player = null;
        try {
            player = commandContext.getSource().getPlayer();
            HitResult looked = player.raycast(40, 0, false);
            BlockPos lookPos = new BlockPos(looked.getPos());
            new DesertBlast(player.getEntityWorld(), lookPos);

        } catch (CommandSyntaxException e) {
            ICBM4Fabric.LOGGER.log(Level.WARNING, e.getMessage());
        }
        return 0;
    }

}
