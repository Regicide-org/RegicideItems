package org.regicide.regicideitems;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.regicide.regicideitems.entities.items.CustomItemManager;
import org.regicide.regicideitems.entities.recipes.RecipeManager;

public final class RegicideItems extends JavaPlugin {
    private static RegicideItems instance;




    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getLogger().info("Loading custom recipes...");
        RecipeManager.readShapedRecipes();
        RecipeManager.readShapelessRecipes();
        CustomItemManager.readCustomItems();
        getLogger().info("All custom recipes was successfully loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static RegicideItems instance() {
        return instance;
    }
}
