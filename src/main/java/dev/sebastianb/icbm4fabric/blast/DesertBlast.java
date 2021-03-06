package dev.sebastianb.icbm4fabric.blast;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.*;

public class DesertBlast extends Blast {


    private Random random = new Random();

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
        super(world, blockPos, 4, false, Explosion.DestructionType.NONE);
        BiomeBlast(world, blockPos);

    }

    private void BiomeBlast(World world, BlockPos blockPos) {
        int radius = 8;

        HashSet<BlockPos> validBlockPos = new HashSet<>();
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos everyBlockPos = new BlockPos(blockPos.add(x, y, z));
                    if (everyBlockPos.isWithinDistance(blockPos, radius)) {
                        if (!(world.getBlockState(everyBlockPos).getBlock() instanceof AirBlock)) {
                            validBlockPos.add(everyBlockPos);
                            if (blocksSetToSand.contains(world.getBlockState(everyBlockPos).getBlock())) {
                                world.setBlockState(everyBlockPos, Blocks.SAND.getDefaultState());
                            }
                            if (blocksSetToSandstone.contains(world.getBlockState(everyBlockPos).getBlock())) {
                                world.setBlockState(everyBlockPos, Blocks.SANDSTONE.getDefaultState());
                            }
                        }
                    }
                }
            }
        }

        for (BlockPos allBlockPos : validBlockPos) {

            if (world.getBlockState(allBlockPos) == Blocks.SAND.getDefaultState()) {
                if (world.getBlockState(allBlockPos.up()) == Blocks.AIR.getDefaultState()) {
                    placeCactus(world, allBlockPos);
                }
            }
        }

        System.out.println(validBlockPos.size());
    }

    private void placeCactus(World world, BlockPos topBlockPositions) {
        BlockPos topLayer = topBlockPositions.up();

        if (!(world.getBlockState(topLayer.north()).getBlock() instanceof AirBlock)) {
            return;
        }
        if (!(world.getBlockState(topLayer.east()).getBlock() instanceof AirBlock)) {
            return;
        }
        if (!(world.getBlockState(topLayer.south()).getBlock() instanceof AirBlock)) {
            return;
        }
        if (!(world.getBlockState(topLayer.west()).getBlock() instanceof AirBlock)) {
            return;
        }
        if (random.nextInt(20) == 0)
            world.setBlockState(topLayer, Blocks.CACTUS.getDefaultState());
        if (random.nextInt(25) == 0)
            world.setBlockState(topLayer, Blocks.DEAD_BUSH.getDefaultState());

    }

}
