package dev.sebastianb.icbm4fabric.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Inject(method = "tickEntity", at = @At("HEAD"))
    private void removeTickingEntity(Entity entity, CallbackInfo ci) {
        try {

        } catch (NullPointerException | IllegalStateException e) {
            System.out.println("CRASH");
            entity.remove();
            System.out.println(e);
        }
    }


//    @Inject(method = "", at = @At("HEAD"))
//    private void removeTickWorld(Entity entity, CallbackInfo ci) {
//        try {
//
//        } catch (NullPointerException | IllegalStateException e) {
//            System.out.println("CRASH");
//            entity.remove();
//            System.out.println(e);
//        }
//    }
    
}
