package dev.fayber.legacygoldfarm.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.zombie.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NeutralMob.class)
interface NeutralMobMixin {
    @Inject(method = "readPersistentAngerSaveData", at = @At("TAIL"))
    private void legacyGoldFarm$resolveLocally(Level level, ValueInput input, CallbackInfo ci) {
        if ((Object) this instanceof ZombifiedPiglin piglin && level instanceof ServerLevel server) {
            EntityReference<LivingEntity> reference = piglin.getPersistentAngerTarget();
            Entity local = reference == null ? null : server.getEntity(reference.getUUID());
            piglin.setTarget(local instanceof LivingEntity living ? living : null);
        }
    }

    @Inject(method = "updatePersistentAnger", at = @At("HEAD"), cancellable = true)
    private void legacyGoldFarm$legacyAnger(ServerLevel level, boolean stayAngryIfTargetPresent, CallbackInfo ci) {
        if (!((Object) this instanceof ZombifiedPiglin piglin)) {
            return;
        }
        LivingEntity target = piglin.getTarget();
        EntityReference<LivingEntity> reference = piglin.getPersistentAngerTarget();
        Entity local = reference == null ? null : level.getEntity(reference.getUUID());
        if ((target == null || target.isDeadOrDying()) && local instanceof Mob) {
            piglin.stopBeingAngry();
            ci.cancel();
            return;
        }
        if (target != null && (reference == null || !reference.matches(target))) {
            piglin.setPersistentAngerTarget(EntityReference.of(target));
            piglin.startPersistentAngerTimer();
        }
        // Old anger counts down for mobs, but pauses while targeting a player.
        if (piglin.getPersistentAngerEndTime() > level.getGameTime()) {
            if (target instanceof Player && stayAngryIfTargetPresent) {
                piglin.setPersistentAngerEndTime(piglin.getPersistentAngerEndTime() + 1);
            }
        } else if (piglin.getPersistentAngerEndTime() > 0) {
            piglin.stopBeingAngry();
        }
        ci.cancel();
    }
}
