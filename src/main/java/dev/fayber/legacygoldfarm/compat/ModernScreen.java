package dev.fayber.legacygoldfarm.compat;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.FarmConfig;
import dev.fayber.legacygoldfarm.config.Revert;
import net.fayber.modernconfig.api.ModernConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ModernScreen {
    private ModernScreen() {}

    public static Screen create(Screen parent) {
        FarmConfig draft = ConfigManager.draft();
        var builder = ModernConfigScreen.builder(Component.literal("Legacy Gold Farm"), parent,
                        () -> ConfigManager.save(draft))
                .category("General")
                .bool("Enable reverts", () -> draft.enabled, value -> draft.enabled = value)
                .tooltip("Master switch. Off bypasses every revert. Dedicated servers use their own config file.")
                .category("Mechanics");
        for (Revert revert : Revert.values()) {
            builder.bool(revert.label, () -> revert.get(draft), value -> revert.set(draft, value))
                    .tooltip(revert.description);
        }
        return builder.build();
    }
}
