package dev.fayber.legacygoldfarm.mixin;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.Revert;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.NetherPortalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBoat.class)
abstract class BoatPortalContactMixin {
    @Inject(method = "tick", at = @At("TAIL"))
    private void legacyGoldFarm$pollPortalContact(CallbackInfo ci) {
        AbstractBoat boat = (AbstractBoat) (Object) this;
        Level level = boat.level();
        if (!ConfigManager.active(Revert.LEGACY_BOAT_PORTAL_CONTACT)
                || level.isClientSide() || boat.isRemoved() || !boat.canUsePortal(false)) {
            return;
        }
        // Use the old occupied-block scan without replacing portal destinations or cooldowns.
        AABB box = boat.getBoundingBox().deflate(0.001);
        BlockPos min = BlockPos.containing(box.minX, box.minY, box.minZ);
        BlockPos max = BlockPos.containing(box.maxX, box.maxY, box.maxZ);
        for (BlockPos pos : BlockPos.betweenClosed(min, max)) {
            if (!level.getChunkSource().hasChunk(pos.getX() >> 4, pos.getZ() >> 4)) {
                continue;
            }
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() instanceof NetherPortalBlock portal) {
                boat.setAsInsidePortal(portal, pos.immutable());
                return;
            }
        }
    }
}
