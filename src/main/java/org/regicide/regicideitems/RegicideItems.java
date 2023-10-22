package org.regicide.regicideitems;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.regicide.regicideitems.entities.recipes.RecipeManager;

import java.io.File;
import java.io.IOException;

public final class RegicideItems extends JavaPlugin {
    private static RegicideItems instance;

    private File customConfigFile;
    private FileConfiguration craftRecipeConfig;

    public static RegicideItems getInstance() {
        return instance;
    }

    public FileConfiguration getCraftRecipeConfig() {
        return craftRecipeConfig;
    }

    private void createCustomConfig() {
        customConfigFile = new File(getDataFolder(),
                "items" + File.separator + "recipes" + File.separator + "craft-recipes.yml");
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            saveResource("items" + File.separator +
                    "recipes" + File.separator + "craft-recipes.yml", false);
        }
        craftRecipeConfig = new YamlConfiguration();
        try {
            craftRecipeConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            getLogger().severe(e.getMessage());
        }
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        createCustomConfig();
        RecipeManager.readCraftRecipes();








    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
