package dev.fayber.legacygoldfarm.config;

import java.util.function.BiConsumer;
import java.util.function.Predicate;

public enum Revert {
    BABY_GEOMETRY("Baby piglin geometry", true,
            "Restore the old baby hitbox and boat riding position. Affects all baby zombified piglins; the current model can extend beyond the old hitbox.",
            c -> c.babyGeometry, (c, value) -> c.babyGeometry = value),
    LOCAL_ANGER_TARGETS("Local anger targets", true,
            "Keep zombified piglin targets in their current dimension. Disables cross-dimension pursuit in other contraptions too.",
            c -> c.localAngerTargets, (c, value) -> c.localAngerTargets = value),
    LEGACY_ANGER_TIMERS("Legacy anger timers", true,
            "Restore countdown for non-player targets. Other llama or golem aggro setups may need repeated provocation. Modern save timing is retained.",
            c -> c.legacyAngerTimers, (c, value) -> c.legacyAngerTimers = value),
    LEGACY_PIGLIN_MELEE("Piglin melee pursuit", false,
            "Use melee pursuit instead of spear charging and repositioning for zombified piglins. Changes their combat and spear-based farms; equipment and drops are unchanged.",
            c -> c.legacyPiglinMelee, (c, value) -> c.legacyPiglinMelee = value);

    public final String label;
    public final boolean defaultValue;
    public final String description;
    private final Predicate<FarmConfig> getter;
    private final BiConsumer<FarmConfig, Boolean> setter;

    Revert(String label, boolean defaultValue, String description,
           Predicate<FarmConfig> getter, BiConsumer<FarmConfig, Boolean> setter) {
        this.label = label;
        this.defaultValue = defaultValue;
        this.description = description;
        this.getter = getter;
        this.setter = setter;
    }

    public boolean get(FarmConfig config) {
        return getter.test(config);
    }

    public void set(FarmConfig config, boolean value) {
        setter.accept(config, value);
    }
}
