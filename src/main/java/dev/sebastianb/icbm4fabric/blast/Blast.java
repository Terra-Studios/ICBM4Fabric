package dev.sebastianb.icbm4fabric.blast;


import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Prefab for all explosions created in-game
 */
public abstract class Blast {

    public Blast(World world, BlockPos blockPos, float power, boolean onFire, Explosion.DestructionType type) {
        world.createExplosion(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), power, onFire, type);
    }



}
