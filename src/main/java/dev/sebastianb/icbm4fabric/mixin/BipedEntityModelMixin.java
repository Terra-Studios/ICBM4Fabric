package dev.sebastianb.icbm4fabric.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.sebastianb.icbm4fabric.item.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;

@Mixin(BipedEntityModel.class)
@Environment(EnvType.CLIENT)
public class BipedEntityModelMixin<T extends LivingEntity> {

    @Shadow @Final public ModelPart leftArm;

    @Shadow @Final public ModelPart rightArm;

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At(value = "RETURN"))
    private void rotateArmsToMatchMissile(T entity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (entity.getMainHandStack().isOf(ModItems.Missiles.TATER.asItem())) {
            this.rightArm.roll = 90.5F;
            this.rightArm.pitch = 0;
            this.rightArm.yaw = 0;

            this.leftArm.roll = -90.5F;
            this.leftArm.pitch = 0;
            this.leftArm.yaw = 0;
        }
    }
}
