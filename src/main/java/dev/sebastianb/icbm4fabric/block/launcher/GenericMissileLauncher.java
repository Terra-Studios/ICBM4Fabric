package dev.sebastianb.icbm4fabric.block.launcher;

import dev.sebastianb.icbm4fabric.item.ModItems;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
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

    public GenericMissileLauncher() {
        super(FabricBlockSettings.of(Material.METAL).strength(5.0F, 6.0F).sounds(BlockSoundGroup.METAL));
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new GenericMissileLauncherEntity(pos, state);
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

//                    return ActionResult.PASS;
                    // sets the missile launcher should be holding
//                    MissileItem missileItem = (MissileItem) player.getStackInHand(hand).getItem();
//                    AbstractMissileProjectile missileEntity = missileItem.getMissile(world);
//                    missileEntity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

//                    world.spawnEntity(missileEntity);

                    BlockEntity blockEntity = world.getBlockEntity(pos); // get the block entity 

                    if (blockEntity instanceof GenericMissileLauncherEntity launcherEntity) {
                        launcherEntity.hasMissile = true; // set the missile in the launcher entity TODO: make this actually store the itemstack
                        System.out.println("has missile");
                    }
                }
            }
        } else {
            if (player.isHolding(ModItems.Missiles.TATER.asItem())) {
                BlockEntity blockEntity = world.getBlockEntity(pos);

                if (blockEntity instanceof GenericMissileLauncherEntity launcherEntity) {
                    launcherEntity.hasMissile = true; // set the missile on the client too
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return GenericMissileLauncherEntity::tick;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
