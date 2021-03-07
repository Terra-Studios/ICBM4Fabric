package dev.sebastianb.icbm4fabric.blast;

import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.PlantBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.gen.decorator.Decoratable;

import java.util.Arrays;
import java.util.HashSet;

public class TaterBlast extends Blast {


    public TaterBlast(World world, BlockPos blockPos) {
        super(world, blockPos, 3, false, Explosion.DestructionType.NONE);
        placeFarmland(world, blockPos);
    }

    private void placeFarmland(World world, BlockPos blockPos) {

        int radius = 6;

        HashSet<BlockPos> validBlockPos = new HashSet<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos everyBlockPos = new BlockPos(blockPos.add(x, y, z));
                    if (everyBlockPos.isWithinDistance(blockPos, radius)) {


                        if (!(world.getBlockState(everyBlockPos).getBlock() instanceof AirBlock)) {
                            validBlockPos.add(everyBlockPos);
                        }
                    }
                }
            }
        }

        for (BlockPos allBlockPos : validBlockPos) {
            if (world.getBlockState(allBlockPos) == Blocks.GRASS_BLOCK.getDefaultState()) {
                world.setBlockState(allBlockPos, Blocks.FARMLAND.getDefaultState());
                world.setBlockState(allBlockPos.up(), Blocks.POTATOES.getDefaultState());

            }
        }


    }
}
