package dev.sebastianb.icbm4fabric.entity.rocket;

import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import dev.sebastianb.icbm4fabric.blast.TaterBlast;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TaterRocketEntity extends AbstractRocketProjectile {



    public TaterRocketEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        player.startRiding(this);
        if (player.isSneaking()) {
            setStage(LaunchStage.LIT);
        }
        return ActionResult.PASS;
    }


    public void launch(BlockPos initialLocation, BlockPos finalLocation, double speed) {
        this.updatePosition(initialLocation.getX(), initialLocation.getY(), initialLocation.getZ());
        super.initialLocation = initialLocation;
    }



    @Override
    public void setCustomNameVisible(boolean visible) {
        super.setCustomNameVisible(true);
    }

    @Override
    public void takeKnockback(float f, double d, double e) {
        super.takeKnockback(0, 0, 0);
    }


    @Override
    public void tick() {
        this.noClip = true;
        this.setCustomName(Text.of(getStage().name()));


        vX = this.getVelocity().getX();
        vY = this.getVelocity().getY();
        vZ = this.getVelocity().getZ();

        switch (getStage()) {
            case IDLE:
                this.setVelocity(0,0,0);
                break;
            case LIT:
                summonParticles(ParticleTypes.FLAME, 10, 0.1, 0); // get rid of when on launched
                this.setVelocity(0,0,0);
                if (timeSinceStage >= 50) { // 200
                    // this.setVelocity(0,8,0);
                    setInitialPosition(this.getBlockPos());
                    System.out.println(this.initialLocation);
                    this.setNoGravity(true);
                    setStage(LaunchStage.LAUNCHED);
                }
                break;
            case LAUNCHED:
                this.noClip = false;
                summonParticles(ParticleTypes.FLAME, 10, 0.07, 0);
                super.tick();
                if (isInsideWall() || isOnGround()) {
                    setStage(LaunchStage.EXPLODED);
                }
                break;
            case EXPLODED:
                System.out.println("EXPLOSION!");
                System.out.println(this.getBlockPos());
                new TaterBlast(world, getBlockPos());
                this.remove();
                break;
            default:
        }

        //Vec3d currentPos = this.getPos();
        timeSinceStage++;
    }



    private void summonParticles(ParticleEffect particleEffect, int times, double multiplier, double yVelocity) {
        for (int x = 0; x < times; x++) {
            this.world.addImportantParticle(particleEffect, true, getX(),getY(),getZ(), randomDouble(multiplier),yVelocity,randomDouble(multiplier));
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
