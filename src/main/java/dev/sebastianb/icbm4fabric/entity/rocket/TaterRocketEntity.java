package dev.sebastianb.icbm4fabric.entity.rocket;

import dev.sebastianb.icbm4fabric.Constants;
import dev.sebastianb.icbm4fabric.api.missile.LaunchStage;
import dev.sebastianb.icbm4fabric.api.missile.MissileEntity;
import dev.sebastianb.icbm4fabric.blast.Blast;
import dev.sebastianb.icbm4fabric.blast.DesertBlast;
import dev.sebastianb.icbm4fabric.blast.TaterBlast;
import io.netty.buffer.Unpooled;
import net.minecraft.client.render.entity.ArrowEntityRenderer;
import net.minecraft.client.util.math.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandler;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

public class TaterRocketEntity extends AbstractEntityProjectile implements MissileEntity {

    private static final TrackedData<LaunchStage> STAGE = DataTracker.registerData(TaterRocketEntity.class, new TrackedDataHandler<LaunchStage>() {
        @Override
        public void write(PacketByteBuf buf, LaunchStage stage) {
            buf.writeEnumConstant(stage);
        }

        @Override
        public LaunchStage read(PacketByteBuf buf) {
            return buf.readEnumConstant(LaunchStage.class);
        }

        @Override
        public LaunchStage copy(LaunchStage stage) {
            return stage;
        }
    });

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        if (tag.contains("Stage")) {
            this.setStage(LaunchStage.valueOf(tag.getString("Stage")));
        }

    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        tag.putString("Stage", getStage().name());
    }


    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(STAGE, LaunchStage.IDLE);
    }



    static {
        TrackedDataHandlerRegistry.register(STAGE.getType());
    }


    public TaterRocketEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (player.isSneaking()) {
            setStage(LaunchStage.LIGHTED);
        }
        return ActionResult.PASS;
    }


    public void launch(BlockPos initialLocation) {
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


    int timeSinceStage = 0;
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
            case LIGHTED:
                summonParticles(ParticleTypes.FLAME, 10, 0.1, 0); // get rid of when on launched
                this.setVelocity(0,0,0);
                if (timeSinceStage >= 200) {
                    this.setVelocity(0,1,0);
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


    @Override
    public LaunchStage getStage() {
        return this.dataTracker.get(STAGE);
    }

    @Override
    public void setStage(LaunchStage stage) {
        if (dataTracker.get(STAGE) != stage) {
            this.dataTracker.set(STAGE, stage);
            timeSinceStage = 0;
        }
    }
}
