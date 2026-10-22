package dev.fayber.legacygoldfarm.mixin;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.Revert;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Mob.class)
abstract class MobTargetMixin {
    @Inject(method = "asValidTarget", at = @At("HEAD"), cancellable = true)
    private void legacyGoldFarm$localTarget(LivingEntity target, CallbackInfoReturnable<LivingEntity> cir) {
        if (ConfigManager.active(Revert.LOCAL_ANGER_TARGETS) && (Object) this instanceof ZombifiedPiglin piglin
                && target != null
                && target.level() != piglin.level()) {
            cir.setReturnValue(null);
        }
    }
}
