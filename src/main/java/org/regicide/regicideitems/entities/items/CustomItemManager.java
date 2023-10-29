package org.regicide.regicideitems.entities.items;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.regicide.regicideitems.RegicideItems;

import java.util.HashMap;
import java.util.Map;

public final class CustomItemManager implements Listener {
    private final static Map<String,ItemStack> CUSTOM_ITEM_MAP = new HashMap<>();
    /**
     * Loads custom item from plugin data folder ./custom-item.yml.
     */
    @EventHandler
    public static void readCustomItems(PlayerJoinEvent event) {
        ItemStack item =new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Test Item");
        meta.getPersistentDataContainer();
        item.setItemMeta(meta);
        //CUSTOM_ITEM_MAP.put(id,item);
        event.getPlayer().getInventory().setItem(2,item);
    }


}
