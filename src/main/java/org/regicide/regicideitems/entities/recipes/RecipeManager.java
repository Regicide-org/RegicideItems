package org.regicide.regicideitems.entities.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.jetbrains.annotations.NotNull;
import org.regicide.regicideitems.RegicideItems;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * This class responsible for loading custom recipes.
 */
public final class RecipeManager {
    /**
     * Loads shaped recipes from plugin data folder ./shaped-recipes.yml.
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static void readShapedRecipes() {
        FileConfiguration shapedRecipesConfig =
                getRecipesConfiguration("items" + File.separator + "recipes" + File.separator + "shaped-recipes.yml");
        ConfigurationSection shapedRecipes = shapedRecipesConfig.getConfigurationSection("shaped-recipes");

        Set<String> recipes = shapedRecipes.getKeys(false);
        for (String recipe : recipes) {

            // Fields of recipe
            String materialName = shapedRecipes.getString(recipe + ".result.item");
            int amount = shapedRecipes.getInt(recipe + ".result.amount");
            List<List<String>> shapes = (List<List<String>>) shapedRecipes.get(recipe + ".shape.shape-patterns");
            ItemStack resultItem = new ItemStack(Material.getMaterial(materialName), amount);


            int i = 0;
            for (List<String> shape : shapes) {

                String s1 = shape.get(0);
                String s2 = shape.get(1);
                String s3 = shape.get(2);

                ShapedRecipe craftRecipe = new ShapedRecipe(new NamespacedKey(RegicideItems.getInstance(),
                        recipe + i), resultItem);
                i++;
                craftRecipe.shape(s1, s2, s3);

                ConfigurationSection cSectionKeys = shapedRecipes.getConfigurationSection(recipe + ".shape.keys");
                assert cSectionKeys != null;
                Set<String> keys = cSectionKeys.getKeys(false);
                for (String key : keys) {
                    char chrKey = key.charAt(0);
                    craftRecipe.setIngredient(chrKey, Material.getMaterial(cSectionKeys.getString("." + key + ".item")));

                }

                Bukkit.addRecipe(craftRecipe);
            }
        }
    }
    /**
     * Loads shapeless recipes from plugin data folder ./shapeless-recipes.yml.
     */
    public static void readShapelessRecipes() {
        FileConfiguration shapelessRecipesConfig =
                getRecipesConfiguration("items" + File.separator + "recipes" + File.separator + "shapeless-recipes.yml");
        // TODO. Надо расписать логику реализации чтения shapelessRecipes
    }

    /**
     * @param path The path to craft recipes.
     * @return The file configuration.
     */
    @SuppressWarnings("all")
    private static FileConfiguration getRecipesConfiguration(@NotNull final String path) {
        File customConfigFile = new File(RegicideItems.getInstance().getDataFolder(), path);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            RegicideItems.getInstance().saveResource(path, false);
        }

        FileConfiguration recipesConfig = new YamlConfiguration();
        try {
            recipesConfig.load(customConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            RegicideItems.getInstance().getLogger().severe(e.getMessage());
        }
        return recipesConfig;
    }

}