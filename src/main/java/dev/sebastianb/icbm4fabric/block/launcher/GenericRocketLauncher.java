package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.entity.ModBlockEntities;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

// TODO: Update to 1.17
public class GenericRocketLauncher extends BlockWithEntity {

    private Constants.LauncherType launcherType = Constants.LauncherType.NOT_AVAILABLE;


    public GenericRocketLauncher(Settings settings, Constants.LauncherType type) {
        super(settings);
        this.launcherType = type;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        switch (launcherType) {
            case TIER1:
                System.out.println("t1");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER1_ROCKET_LAUNCHER, pos, state);
            case TIER2:
                System.out.println("t2");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER2_ROCKET_LAUNCHER, pos, state);
            case TIER3:
                System.out.println("t3");
                return new GenericRocketLauncherEntity(ModBlockEntities.TIER3_ROCKET_LAUNCHER, pos, state);
            default:
                return null; // TODO: fix with a failsafe later lol
        }
    }


    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }
//        if (world.isClient) {
//            if (hand == Hand.OFF_HAND)
//                return ActionResult.FAIL;
//            PacketByteBuf buf = PacketByteBufs.create().writeBlockPos(pos);
//            ClientPlayNetworking.send(Constants.Packets.SUMMON_MISSILE, buf); // sends where the launcher is to the server
//        }
        return ActionResult.SUCCESS;
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
