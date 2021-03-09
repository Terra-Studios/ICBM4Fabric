package dev.sebastianb.icbm4fabric.entity.rocket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Packet;
import net.minecraft.util.Arm;
import net.minecraft.world.World;

public class TaterRocketEntity extends MobEntity {

    public TaterRocketEntity(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

}
