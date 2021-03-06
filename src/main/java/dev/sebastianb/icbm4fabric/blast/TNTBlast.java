package dev.sebastianb.icbm4fabric.blast;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TNTBlast extends Blast {

    public TNTBlast(World world, BlockPos blockPos) {
        super(world, blockPos, 5, false, Explosion.DestructionType.BREAK.BREAK);

    }




}
