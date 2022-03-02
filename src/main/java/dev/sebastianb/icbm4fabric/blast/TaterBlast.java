package dev.sebastianb.icbm4fabric.blast;

import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Random;

public class TaterBlast extends Blast {

    private final Random random = new Random();

    public TaterBlast(World world, BlockPos blockPos) {
        super(world, blockPos, 4, false, Explosion.DestructionType.NONE, true, 6);
        placeFarmland(world);
    }

    private void placeFarmland(World world) {
        for (BlockPos allBlockPos : allBlockPosNotAir) {
            if (world.getBlockState(allBlockPos) == Blocks.GRASS_BLOCK.getDefaultState()) {
                if (world.getBlockState(allBlockPos.up()) == Blocks.AIR.getDefaultState()) {
                    world.setBlockState(allBlockPos, Blocks.FARMLAND.getDefaultState());
                    world.setBlockState(allBlockPos.up(), Blocks.POTATOES.getDefaultState().with(CropBlock.AGE, random.nextInt(8)));
                }
            }
        }
    }
    
}
