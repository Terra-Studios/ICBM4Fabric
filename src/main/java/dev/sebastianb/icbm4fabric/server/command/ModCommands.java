package dev.sebastianb.icbm4fabric.server.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.missile.MissileManager;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.logging.Level;

public class ModCommands {

    public static void register() {

        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {

            //TODO: Rework the command to be any explosion with coord positions
            dispatcher.register(
                    CommandManager.literal("summontestexplosion")
                    .executes(ModCommands::summonExplosion)
            );

            dispatcher.register(
                    CommandManager.literal("clear_missiles")
                            .executes(ModCommands::clearMissiles)
            );

        }));

    }

    private static int summonExplosion(CommandContext<ServerCommandSource> commandContext) {
        PlayerEntity player = null;
        try {
            player = commandContext.getSource().getPlayer();
            HitResult looked = player.raycast(40, 0, false);
            BlockPos lookPos = new BlockPos(looked.getPos());
            World world = player.getEntityWorld();
            //new DesertBlast(player.getEntityWorld(), lookPos);

            AbstractMissileProjectile missileEntity = ModEntityTypes.Missiles.TATER.getType().create(world);

            world.spawnEntity(missileEntity);

        } catch (CommandSyntaxException e) {
            ICBM4Fabric.LOGGER.log(Level.WARNING, e.getMessage());
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int clearMissiles(CommandContext<ServerCommandSource> commandContext) { // add a command to clear the missiles
        MissileManager.missileManager.clearMissiles();

        return Command.SINGLE_SUCCESS;
    }

}
