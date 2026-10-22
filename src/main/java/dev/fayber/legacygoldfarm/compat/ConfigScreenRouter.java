package dev.fayber.legacygoldfarm.compat;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screens.Screen;

public final class ConfigScreenRouter {
    private ConfigScreenRouter() {}

    public static Screen create(Screen parent) {
        FabricLoader loader = FabricLoader.getInstance();
        if (loader.isModLoaded("modernconfig")) {
            return ModernScreen.create(parent);
        }
        if (loader.isModLoaded("cloth-config")) {
            return ClothScreen.create(parent);
        }
        return null;
    }
}
