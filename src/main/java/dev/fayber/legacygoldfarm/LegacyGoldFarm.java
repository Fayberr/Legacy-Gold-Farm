package dev.fayber.legacygoldfarm;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import net.fabricmc.api.ModInitializer;

public final class LegacyGoldFarm implements ModInitializer {
    @Override
    public void onInitialize() {
        ConfigManager.load();
    }
}
