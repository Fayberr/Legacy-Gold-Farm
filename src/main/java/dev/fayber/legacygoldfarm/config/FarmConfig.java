package dev.fayber.legacygoldfarm.config;

public final class FarmConfig {
    public boolean enabled = true;
    public boolean babyGeometry = true;
    public boolean localAngerTargets = true;
    public boolean legacyAngerTimers = true;
    public boolean legacyPiglinMelee = false;

    public FarmConfig copy() {
        FarmConfig copy = new FarmConfig();
        copy.enabled = enabled;
        for (Revert revert : Revert.values()) {
            revert.set(copy, revert.get(this));
        }
        return copy;
    }
}
