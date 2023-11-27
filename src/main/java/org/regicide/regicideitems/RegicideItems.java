package org.regicide.regicideitems;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.regicide.regicideitems.entities.items.CustomItemManager;
import org.regicide.regicideitems.entities.recipes.RecipeManager;

public final class RegicideItems extends JavaPlugin {
    private static RegicideItems instance;

    public static RegicideItems getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        getLogger().info("Loading custom recipes...");
        RecipeManager.readShapedRecipes();
        RecipeManager.readShapelessRecipes();
        Bukkit.getServer().getPluginManager().registerEvents(new CustomItemManager(),this);
        getLogger().info("All custom recipes was successfully loaded!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
