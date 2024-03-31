package org.regicide.regicideitems.entities.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.regicide.regicideitems.RegicideItems;
import org.regicide.regicideitems.config.ConfigManager;
import org.regicide.regicideitems.entities.items.CustomItemManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.util.List;
import java.util.Set;

import static javax.sql.rowset.spi.SyncFactory.getLogger;

/**
 * This class responsible for loading custom recipes.
 */
public final class RecipeManager {
    public static void readShapedRecipesToCustom() {
        FileConfiguration config = ConfigManager.getConfigFromFile("items" + File.separator + "recipes" + File.separator + "shaped-recipe-custom.yml");
        if (config == null) {
            RegicideItems.instance().getLogger().warning("Cannot load shaped recipes from config.");
            return;
        }

        ConfigurationSection recipesSection = config.getConfigurationSection("shaped-recipe-custom");
        if (recipesSection == null) {
            RegicideItems.instance().getLogger().warning("No shaped recipes found in config.");
            return;
        }

        Set<String> recipeKeys = recipesSection.getKeys(false);
        for (String recipeKey : recipeKeys) {
            ConfigurationSection recipeSection = recipesSection.getConfigurationSection(recipeKey);

            ConfigurationSection resultSection = recipeSection.getConfigurationSection("result");
            String resultItemName = resultSection.getString("item");
            int resultAmount = resultSection.getInt("amount");
            ItemStack resultItem;

            resultItem = CustomItemManager.getCustomItem(resultItemName);
            ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(RegicideItems.instance(), recipeKey), resultItem);

            List<String> shapePatterns = recipeSection.getStringList("shape.shape-patterns");
            shapedRecipe.shape(shapePatterns.toArray(new String[0]));

            ConfigurationSection keysSection = recipeSection.getConfigurationSection("keys");
            Set<String> keyNames = keysSection.getKeys(false);
            for (String keyName : keyNames) {
                char keyChar = keyName.charAt(0);
                Material keyMaterial = Material.getMaterial(keysSection.getString(keyName + ".item"));
                shapedRecipe.setIngredient(keyChar, keyMaterial);
            }

            Bukkit.addRecipe(shapedRecipe);
        }
    }

    /**
     * Loads shaped recipes from plugin data folder ./shaped-recipes.yml.
     */
    @SuppressWarnings({"ConstantConditions", "unchecked"})
    public static void readShapedRecipes() {
        FileConfiguration shapedRecipesConfig =
                ConfigManager.getConfigFromFile("items" + File.separator + "recipes" + File.separator + "shaped-recipes.yml");
        ConfigurationSection shapedRecipes = shapedRecipesConfig.getConfigurationSection("shaped-recipes");

        Set<String> recipes = shapedRecipes.getKeys(false);
        for (String recipe : recipes) {

            // Fields of recip
            String materialName = shapedRecipes.getString(recipe + ".result.item");
            int amount = shapedRecipes.getInt(recipe + ".result.amount");
            List<List<String>> shapes = (List<List<String>>) shapedRecipes.get(recipe + ".shape.shape-patterns");
            ItemStack resultItem = new ItemStack(Material.getMaterial(materialName), amount);


            int i = 0;
            for (List<String> shape : shapes) {

                String s1 = shape.get(0);
                String s2 = shape.get(1);
                String s3 = shape.get(2);

                ShapedRecipe craftRecipe = new ShapedRecipe(new NamespacedKey(RegicideItems.instance(),
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
                ConfigManager.getConfigFromFile("items" + File.separator + "recipes" + File.separator + "shapeless-recipes.yml");
        ConfigurationSection shapelessRecipe = shapelessRecipesConfig.getConfigurationSection("shapeless-recipes");

        Set<String> recipesLess = shapelessRecipe.getKeys(false);
        for (String recipeLess : recipesLess) {

            // Fields of recipeLess
            String materialLessName = shapelessRecipe.getString(recipeLess+".result.item");
            int amountOutLess = shapelessRecipe.getInt(recipeLess+".result.amount");
            int amountPutLess = shapelessRecipe.getInt(recipeLess+".key.amount");
            String keyLess = shapelessRecipe.getString(recipeLess+".key.item");
            ItemStack resultItemLess = new ItemStack(Material.getMaterial(materialLessName), amountOutLess);


            ShapelessRecipe craftRecipeLess = new ShapelessRecipe(new NamespacedKey(RegicideItems.instance(),
                    recipeLess),resultItemLess);

            craftRecipeLess.addIngredient(amountPutLess,Material.getMaterial(keyLess));

            Bukkit.addRecipe(craftRecipeLess);
        }
    }
}