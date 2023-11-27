package org.regicide.regicideitems.entities.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.regicide.regicideitems.RegicideItems;
import org.regicide.regicideitems.config.ConfigManager;

import java.awt.*;
import java.io.File;
import java.util.*;
import java.util.List;

public final class CustomItemManager implements Listener {
    private final static Map<String,ItemStack> CUSTOM_ITEM_MAP = new HashMap<>();
    /**
     * Loads custom item from plugin data folder ./custom-item.yml.
     */
    @EventHandler
    public static void readCustomItems(PlayerJoinEvent event) {
        FileConfiguration customConfigItems =
                ConfigManager.getConfigFromFile("items" + File.separator + "custom-items.yml");
        ConfigurationSection customItems =  customConfigItems.getConfigurationSection("custom-items");

        int i = 1;
        Set<String> items = customItems.getKeys(false);
        for (String item : items) {

            //Fields of item
            String materialItemName = customItems.getString(item+".type");
            String nmitem = customItems.getString(item+".name");
            List<Component> loreitem = new ArrayList<>();
            for (String str : customItems.getStringList(item+".lore"))
                loreitem.add(Component.text(str).toBuilder().build());

            ItemStack newitem = new ItemStack(Material.getMaterial(materialItemName));
            ItemMeta meta = newitem.getItemMeta();
            meta.displayName(Component.text(nmitem).toBuilder().build());
            meta.lore(loreitem);
            newitem.setItemMeta(meta);
            CUSTOM_ITEM_MAP.put(item,newitem);
            event.getPlayer().getInventory().setItem(i,newitem);
            i++;

        }
    }
}
