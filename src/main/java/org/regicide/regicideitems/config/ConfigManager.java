package org.regicide.regicideitems.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.regicide.regicideitems.RegicideItems;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    /**
     * @param path The path to craft recipes.
     * @return The file configuration.
     */
    public static FileConfiguration getConfigFromFile(@NotNull final String path) {
        File customConfigFile = new File(RegicideItems.instance().getDataFolder(), path);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            RegicideItems.instance().saveResource(path, false);
        }

        FileConfiguration recipesConfig = new YamlConfiguration();
        try {
            recipesConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            RegicideItems.instance().getLogger().severe(e.getMessage());
        }
        return recipesConfig;
    }
}
