package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import dev.sebastianb.icbm4fabric.item.ModItems;
import dev.sebastianb.icbm4fabric.item.missile.MissileItem;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class GenericMissileLauncher extends BlockWithEntity {

    GenericMissileLauncherEntity blockEntity;

    public GenericMissileLauncher() {
        super(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        blockEntity = new GenericMissileLauncherEntity(pos, state);
        return blockEntity;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
            if (screenHandlerFactory != null) {
                if (!player.isHolding(ModItems.Missiles.TATER.asItem())) {
                    // allow player to open screen handler without missile
                    player.openHandledScreen(screenHandlerFactory);
                } else {
                    // sets the missile launcher should be holding
                    MissileItem missileItem = (MissileItem) player.getStackInHand(hand).getItem();
                    AbstractMissileProjectile missileEntity = missileItem.getMissile(world);
                    missileEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

                    world.spawnEntity(missileEntity);
                }
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
