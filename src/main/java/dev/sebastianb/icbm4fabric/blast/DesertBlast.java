package dev.sebastianb.icbm4fabric.blast;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class DesertBlast extends Blast {


    private final Random random = new Random();

    private final HashSet<Block> blocksSetToSand = new HashSet<>(
            Arrays.asList(
                    Blocks.DIRT,
                    Blocks.GRASS_BLOCK
            )
    );
    private final HashSet<Block> blocksSetToSandstone = new HashSet<>(
            Arrays.asList(
                    Blocks.STONE,
                    Blocks.INFESTED_STONE
            )
    );



    public DesertBlast(World world, BlockPos blockPos) {
        super(world, blockPos, 4, false, Explosion.DestructionType.NONE, true, 8);
        AreaDesertfication(world);

    }

    // TODO: Slightly less shit then before since abstracted. Still fix and see where can be optimized.
    private void AreaDesertfication(World world) {
        for (BlockPos allBlockPos: allBlockPosNotAir) {
            if (blocksSetToSand.contains(world.getBlockState(allBlockPos).getBlock())) {
                world.setBlockState(allBlockPos, Blocks.SAND.getDefaultState());
            }
            if (blocksSetToSandstone.contains(world.getBlockState(allBlockPos).getBlock())) {
                world.setBlockState(allBlockPos, Blocks.SANDSTONE.getDefaultState());
            }
        }

        for (BlockPos allBlockPos : allBlockPosNotAir) {
            if (world.getBlockState(allBlockPos) == Blocks.SAND.getDefaultState()) {
                if (world.getBlockState(allBlockPos.up()) == Blocks.AIR.getDefaultState()) {
                    placeCactus(world, allBlockPos);
                }
            }
        }
    }

    // TODO: Shit code please improve later 💩
    private void placeCactus(World world, BlockPos topBlockPositions) {
        BlockPos topLayer = topBlockPositions.up();

        if (!(world.getBlockState(topLayer.north()).isAir()))
            return;
        if (!(world.getBlockState(topLayer.east()).isAir()))
            return;
        if (!(world.getBlockState(topLayer.south()).isAir()))
            return;
        if (!(world.getBlockState(topLayer.west()).isAir()))
            return;


        if (random.nextInt(20) == 0)
            world.setBlockState(topLayer, Blocks.CACTUS.getDefaultState());
        if (random.nextInt(25) == 0)
            world.setBlockState(topLayer, Blocks.DEAD_BUSH.getDefaultState());

    }
}
