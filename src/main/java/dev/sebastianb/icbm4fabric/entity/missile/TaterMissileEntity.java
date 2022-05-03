package dev.sebastianb.icbm4fabric.entity.missile;

import dev.sebastianb.icbm4fabric.ICBM4Fabric;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.blast.TaterBlast;
import dev.sebastianb.icbm4fabric.entity.missile.path.LaunchPaths;
import net.minecraft.entity.EntityType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.logging.Level;

public class TaterMissileEntity extends AbstractMissileProjectile {


    public TaterMissileEntity(EntityType<? extends AbstractMissileProjectile> entityType, World world) {
        super(entityType, world);
    }


    public void launch(BlockPos initialLocation, BlockPos finalLocation, double speed) { // unused and maybe could be removed
        this.updatePosition(initialLocation.getX(), initialLocation.getY(), initialLocation.getZ());
        super.initialLocation = initialLocation;
    }

    @Override
    public void tick() {
        this.noClip = true;
        this.setCustomName(Text.of(getStage().name()));

        vX = this.getVelocity().getX();
        vY = this.getVelocity().getY();
        vZ = this.getVelocity().getZ(); // set individual velocity components 

        switch (getStage()) {
            case IDLE:
                this.setPitch(90);
                this.setVelocity(0,0,0);
                updateMotion = false; // don't move
                break;
            case LIT:
                this.setPitch(90);
                this.setVelocity(0,0,0);
                summonParticles(ParticleTypes.FLAME, 10, 0.1, 0); // get rid of when on launched
                this.setVelocity(0,0,0);
                if (timeSinceStage >= 50) { // 200
                    // this.setVelocity(0,8,0);
                    setInitialBlockPos(this.getBlockPos());
                    this.setNoGravity(true);
                    setPath(LaunchPaths.BezierPath, 120);
                    setStage(LaunchStage.LAUNCHED);
                }
                break;
            case LAUNCHED:
                updateMotion = true;
                this.noClip = false;
                summonParticles(ParticleTypes.FLAME, 10, 0.07, 0);
                super.tick();
                if (isInsideWall() || isOnGround()) {
                    setStage(LaunchStage.EXPLODED);
                }
                break;
            case EXPLODED:
                ICBM4Fabric.LOGGER.log(Level.INFO, "EXPLOSION!");
                ICBM4Fabric.LOGGER.log(Level.INFO, "LOCATION = " + getBlockPos());
                new TaterBlast(world, getBlockPos());
                this.remove(RemovalReason.DISCARDED);
                break;
            default:
        }

        timeSinceStage++;
    }


    private void summonParticles(ParticleEffect particleEffect, int times, double multiplier, double yVelocity) {
        Vec3d pos = getPos().subtract(getVelocity());

        for (int x = 0; x < times; x++) {
            this.world.addImportantParticle(particleEffect, true, pos.getX(), pos.getY(), pos.getZ(), randomDouble(multiplier) ,yVelocity, randomDouble(multiplier));
        }
    }


    private double randomDouble(double multiplier) {
        if (random.nextBoolean()) {
            return (random.nextDouble() - 0.5) * multiplier;
        } else {
            return (-random.nextDouble() + 0.5) * multiplier;
        }
    }
}
