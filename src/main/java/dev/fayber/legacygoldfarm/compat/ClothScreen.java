package dev.fayber.legacygoldfarm.compat;

import dev.fayber.legacygoldfarm.config.ConfigManager;
import dev.fayber.legacygoldfarm.config.FarmConfig;
import dev.fayber.legacygoldfarm.config.Revert;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public final class ClothScreen {
    private ClothScreen() {}

    public static Screen create(Screen parent) {
        FarmConfig draft = ConfigManager.draft();
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent)
                .setTitle(Component.literal("Legacy Gold Farm"))
                .setSavingRunnable(() -> ConfigManager.save(draft));
        var entries = builder.entryBuilder();
        var general = builder.getOrCreateCategory(Component.literal("General"));
        general.addEntry(entries.startBooleanToggle(Component.literal("Enable reverts"), draft.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.literal("Master switch. Off bypasses every revert. Dedicated servers use their own config file."))
                .setSaveConsumer(value -> draft.enabled = value).build());
        var reverts = builder.getOrCreateCategory(Component.literal("Mechanics"));
        for (Revert revert : Revert.values()) {
            reverts.addEntry(entries.startBooleanToggle(Component.literal(revert.label), revert.get(draft))
                    .setDefaultValue(revert.defaultValue)
                    .setTooltip(Component.literal(revert.description))
                    .setSaveConsumer(value -> revert.set(draft, value)).build());
        }
        return builder.build();
    }
}
