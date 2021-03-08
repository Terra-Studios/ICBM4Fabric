package dev.sebastianb.icbm4fabric.blast;


import net.minecraft.block.AirBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

import java.util.HashSet;

/**
 * Prefab for all explosions created in-game
 */
public abstract class Blast {

    protected HashSet<BlockPos> allBlockPosNotAir = new HashSet<>();

    public Blast(World world, BlockPos blockPos, float power, boolean onFire, Explosion.DestructionType type) {
        world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), power, onFire, type);

    }

    public Blast(World world, BlockPos blockPos, float power, boolean onFire, Explosion.DestructionType type, boolean grabValidBlockLocations, int radius) {
        world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), power, onFire, type);
        if (grabValidBlockLocations) {
            setValidBlockLocations(world, blockPos, radius);
        }
    }


    /**
     * Not a setter method! Stores any position that's not a air block
     * Useful for other methods in parent class.
     * See {@link TaterBlast#placeFarmland(World)} for example.
     * Uses {@link Blast#allBlockPosNotAir} in the parent class
     *
     * @param world World where player is being stored
     * @param blockPos Center where explosion takes place
     * @param radius How many blocks from the block pos?
     */
    private void setValidBlockLocations(World world, BlockPos blockPos, int radius) {
        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {
                    BlockPos everyBlockPos = new BlockPos(blockPos.add(x, y, z));
                    if (everyBlockPos.isWithinDistance(blockPos, radius)) {
                        if (!(world.getBlockState(everyBlockPos).getBlock() instanceof AirBlock)) {
                            allBlockPosNotAir.add(everyBlockPos);
                        }
                    }
                }
            }
        }
    }




}