package org.regicide.regicideitems.entities.recipes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.regicide.regicideitems.RegicideItems;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public final class RecipeManager {
    public static void readCraftRecipes() {

        ConfigurationSection cSection = RegicideItems.getInstance().getCraftRecipeConfig().getConfigurationSection("craft-recipes");
        Set<String> recipes = cSection.getKeys(false);
        for (String recipe : recipes) {
            String materialName = cSection.getString(recipe + ".result.item");
            int amount = cSection.getInt(recipe + ".result.amount");
            boolean shapeBool = cSection.getBoolean(recipe + ".shape.shaped");
            List<List<String>> shapes = (List<List<String>>) cSection.get(recipe + ".shape.shape-patterns");

            ItemStack item = new ItemStack(Material.getMaterial(materialName), amount);

            int i = 0;
            if (shapeBool) {
                for (List<String> shape : shapes) {
                    String s1 = shape.get(0);
                    String s2 = shape.get(1);
                    String s3 = shape.get(2);
                    ShapedRecipe craftRecipe = new ShapedRecipe(new NamespacedKey(RegicideItems.getInstance(),
                            recipe + i), item);
                    i++;
                    craftRecipe.shape(s1, s2, s3);

                    ConfigurationSection cSectionKeys = cSection.getConfigurationSection(recipe + ".shape.keys");
                    Set<String> keys = cSectionKeys.getKeys(false);
                    for (String key : keys) {
                        char chrKey = key.charAt(0);
                        craftRecipe.setIngredient(chrKey, Material.getMaterial(cSectionKeys.getString("." + key + ".item")));

                    }

                    Bukkit.addRecipe(craftRecipe);
                }

            }
        }




    }

}