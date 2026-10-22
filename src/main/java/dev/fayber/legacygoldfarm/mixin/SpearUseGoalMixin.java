package dev.fayber.legacygoldfarm.mixin;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.Revert;
import net.minecraft.world.entity.ai.goal.SpearUseGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpearUseGoal.class)
abstract class SpearUseGoalMixin {
    @Shadow @Final private Monster mob;

    @Inject(method = {"canUse", "canContinueToUse"}, at = @At("HEAD"), cancellable = true)
    private void legacyGoldFarm$meleePursuit(CallbackInfoReturnable<Boolean> cir) {
        if (mob instanceof ZombifiedPiglin && ConfigManager.active(Revert.LEGACY_PIGLIN_MELEE)) {
            // Let the existing ZombieAttackGoal take over, including after a live toggle.
            cir.setReturnValue(false);
        }
    }
}
