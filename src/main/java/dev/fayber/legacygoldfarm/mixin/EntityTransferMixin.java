package dev.fayber.legacygoldfarm.mixin;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.Revert;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
abstract class EntityTransferMixin {
    @Inject(method = "restoreFrom", at = @At("TAIL"))
    private void legacyGoldFarm$preserveRemainingAnger(Entity oldEntity, CallbackInfo ci) {
        if (ConfigManager.active(Revert.LEGACY_ANGER_TIMERS) && (Object) this instanceof ZombifiedPiglin piglin
                && oldEntity instanceof ZombifiedPiglin old) {
            long end = old.getPersistentAngerEndTime();
            if (end > 0) {
                long remaining = Math.max(0, end - old.level().getGameTime());
                piglin.setPersistentAngerEndTime(piglin.level().getGameTime() + remaining);
            }
        }
    }
}
