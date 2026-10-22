package dev.fayber.legacygoldfarm.mixin;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.Revert;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
abstract class PiglinGeometryRefreshMixin {
    @Unique
    private boolean legacyGoldFarm$oldGeometry;

    @Inject(method = "tick", at = @At("HEAD"))
    private void legacyGoldFarm$refreshDimensions(CallbackInfo ci) {
        if ((Object) this instanceof ZombifiedPiglin piglin) {
            boolean oldGeometry = ConfigManager.active(Revert.BABY_GEOMETRY);
            if (oldGeometry != legacyGoldFarm$oldGeometry) {
                legacyGoldFarm$oldGeometry = oldGeometry;
                piglin.refreshDimensions();
            }
        }
    }
}
