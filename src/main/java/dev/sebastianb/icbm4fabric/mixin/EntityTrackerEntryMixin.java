package dev.sebastianb.icbm4fabric.mixin;

import dev.sebastianb.icbm4fabric.entity.missile.AbstractMissileProjectile;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.server.network.EntityTrackerEntry;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTrackerEntry.class)
public class EntityTrackerEntryMixin {

    // lol I'm trying

    // TODO: replace with this lib later: https://github.com/LlamaLad7/MixinExtras/wiki/ModifyExpressionValue
    // this is because Redirect will likely break other mods that use this method based from MixinExtras wiki
    @Redirect(method = "tick",
            slice = @Slice(
                    from = @At(value = "INVOKE",
                            target = "Lnet/minecraft/network/packet/s2c/play/EntityS2CPacket;decodePacketCoordinates(JJJ)Lnet/minecraft/util/math/Vec3d;"),
                    to = @At(value = "INVOKE",
                            target = "Lnet/minecraft/network/packet/s2c/play/EntityS2CPacket;encodePacketCoordinate(D)J")),
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/server/network/EntityTrackerEntry;entity:Lnet/minecraft/entity/Entity;",
                    opcode = Opcodes.GETFIELD,
                    shift = At.Shift.AFTER))
    public boolean onInstanceOf(Object entity, Class<?> clazz) {
        return entity instanceof PersistentProjectileEntity || entity instanceof AbstractMissileProjectile;
    }

    @Redirect(method = "tick",
            slice = @Slice(
                    from = @At(value = "INVOKE",
                            target = "net/minecraft/network/packet/s2c/play/EntityPositionS2CPacket.<init>(Lnet/minecraft/entity/Entity;)V"),
                    to = @At(value = "NEW",
                            target = "net/minecraft/network/packet/s2c/play/EntityS2CPacket$RotateAndMoveRelative")),
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/server/network/EntityTrackerEntry;entity:Lnet/minecraft/entity/Entity;",
                    opcode = Opcodes.GETFIELD,
                    shift = At.Shift.AFTER))
    public boolean onInstanceOf2(Object entity, Class<?> clazz) {
        return entity instanceof PersistentProjectileEntity || entity instanceof AbstractMissileProjectile;
    }

}
