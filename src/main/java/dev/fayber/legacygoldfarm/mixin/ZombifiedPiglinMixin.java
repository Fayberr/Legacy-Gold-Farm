package dev.fayber.legacygoldfarm.mixin;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombifiedPiglin.class)
abstract class ZombifiedPiglinMixin {
    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    private void legacyGoldFarm$babyDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        ZombifiedPiglin piglin = (ZombifiedPiglin) (Object) this;
        if (piglin.isBaby()) {
            // The 1.21.11 definition includes the scaled riding attachment.
            cir.setReturnValue(piglin.getType().getDimensions().scale(0.5F).withEyeHeight(0.97F));
        }
    }
}
