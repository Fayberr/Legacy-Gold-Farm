package dev.fayber.legacygoldfarm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class ConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger("legacy_gold_farm");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path FILE = FabricLoader.getInstance().getConfigDir().resolve("legacy-gold-farm.json");
    private static volatile FarmConfig current = new FarmConfig();

    private ConfigManager() {}

    public static boolean active(Revert revert) {
        FarmConfig config = current;
        return config.enabled && revert.get(config);
    }

    public static FarmConfig draft() {
        return current.copy();
    }

    public static void load() {
        if (!Files.exists(FILE)) {
            save(new FarmConfig());
            return;
        }
        try {
            FarmConfig loaded = GSON.fromJson(Files.readString(FILE), FarmConfig.class);
            if (loaded == null) {
                throw new IllegalArgumentException("Config must be a JSON object");
            }
            current = loaded;
        } catch (IOException | RuntimeException e) {
            LOGGER.error("Could not read {}. Using defaults; file left unchanged.", FILE, e);
        }
    }

    public static synchronized void save(FarmConfig draft) {
        FarmConfig saved = draft.copy();
        Path temporary = FILE.resolveSibling(FILE.getFileName() + ".tmp");
        try {
            Files.createDirectories(FILE.getParent());
            Files.writeString(temporary, GSON.toJson(saved) + "\n");
            try {
                Files.move(temporary, FILE, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (java.nio.file.AtomicMoveNotSupportedException e) {
                Files.move(temporary, FILE, StandardCopyOption.REPLACE_EXISTING);
            }
            current = saved;
        } catch (IOException e) {
            LOGGER.error("Could not save {}. Active settings were not changed.", FILE, e);
        }
    }
}
