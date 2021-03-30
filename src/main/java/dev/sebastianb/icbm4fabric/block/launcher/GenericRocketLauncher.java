package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import dev.sebastianb.icbm4fabric.entity.ModEntityTypes;
import dev.sebastianb.icbm4fabric.entity.rocket.TaterRocketEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.SummonCommand;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GenericRocketLauncher extends Block implements BlockEntityProvider {

    private Constants.LauncherType launcherType = Constants.LauncherType.NOT_AVAILABLE;


    public GenericRocketLauncher(Settings settings, Constants.LauncherType type) {
        super(settings);
        this.launcherType = type;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockView world) {
        switch (launcherType) {
            case TIER1:
                System.out.println("t1");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER1_ROCKET_LAUNCHER);
            case TIER2:
                System.out.println("t2");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER2_ROCKET_LAUNCHER);
            case TIER3:
                System.out.println("t3");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER3_ROCKET_LAUNCHER);
            default:
                return null; // TODO: fix with a failsafe later lol
        }
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            if (hand == Hand.OFF_HAND)
                return ActionResult.FAIL;
            PacketByteBuf buf = PacketByteBufs.create().writeBlockPos(pos);
            ClientPlayNetworking.send(Constants.Packets.SUMMON_MISSILE, buf); // sends where the launcher is to the server
        }
        return ActionResult.PASS;
    }


}
