package org.regicide.regicideitems.entities.items;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.regicide.regicideitems.RegicideItems;
import org.regicide.regicideitems.config.ConfigManager;
import java.io.File;
import java.util.*;
import java.util.List;

public final class CustomItemManager{
    private final static Map<String,ItemStack> CUSTOM_ITEM_MAP = new HashMap<>();
    
    /**
     * Loads custom item from plugin data folder ./custom-item.yml.
     */

    public static void readCustomItems() {
        FileConfiguration customConfigItems =
                ConfigManager.getConfigFromFile("items" + File.separator + "custom-items.yml");
        ConfigurationSection customItems =  customConfigItems.getConfigurationSection("custom-items");

        int i = 1;
        Set<String> items = customItems.getKeys(false);
        for (String item : items) {
            
            
            //Fields of item
            String materialItemName = customItems.getString(item+".type");

            //Meta
            String nmitem = customItems.getString(item+".meta.name");
            ItemStack newitem = new ItemStack(Material.getMaterial(materialItemName));
            ItemMeta meta = newitem.getItemMeta();
            List<Component> loreitem = new ArrayList<>();
            for (String str : customItems.getStringList(item+".meta.lore"))
                loreitem.add(Component.text(str).toBuilder().build());

            if (customItems.contains(item+".meta.persistent-data") ) {
                String dk = customItems.getString(item+".meta.persistent-data.data-key");
                String dt = customItems.getString(item+".meta.persistent-data.data-type");
                Object dv = customItems.get(item+".meta.persistent-data.data-value");
                setPersistentData(item,meta,dt,dk,dv);
            }

            
            meta.displayName(Component.text(nmitem).toBuilder().build());
            meta.lore(loreitem);
            newitem.setItemMeta(meta);
            CUSTOM_ITEM_MAP.put(item,newitem);
            i++;

        }
    }
    private static void setPersistentData(@NotNull String ID, ItemMeta meta,String dt,String dk,Object dv){
        PersistentDataType persistentDataType;
        NamespacedKey nms = new NamespacedKey(RegicideItems.instance(),dk);
        switch (dt) {
            case "BYTE" : {
                persistentDataType = PersistentDataType.BYTE;
                Byte dataValue = (Byte) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "BYTE-ARRAY" : {
                persistentDataType = PersistentDataType.BYTE_ARRAY;
                Byte[] dataValue = (Byte[]) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "DOUBLE" : {
                persistentDataType = PersistentDataType.DOUBLE;
                Double dataValue = (Double) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "FLOAT" : {
                persistentDataType = PersistentDataType.FLOAT;
                Float dataValue = (Float) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "INTEGER" : {
                persistentDataType = PersistentDataType.INTEGER;
                Integer dataValue = (Integer) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "INTEGER-ARRAY" : {
                persistentDataType = PersistentDataType.INTEGER_ARRAY;
                Integer[] dataValue = (Integer[]) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "LONG" : {
                persistentDataType = PersistentDataType.LONG;
                Long dataValue = (Long) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "LONG-ARRAY" : {
                persistentDataType = PersistentDataType.LONG_ARRAY;
                Long[] dataValue = (Long[]) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "SHORT" : {
                persistentDataType = PersistentDataType.SHORT;
                Short dataValue = (Short) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "STRING" : {
                persistentDataType = PersistentDataType.STRING;
                String dataValue = (String) dv;
                meta.getPersistentDataContainer().set(nms, persistentDataType, dataValue);
                break;
            }
            default:{
                RegicideItems.instance().getLogger().warning("Field \"data-type\" for the item \"" + ID + "\" is set incorrectly!");
                RegicideItems.instance().getLogger().warning("Here is a list of acceptable values for this field:");
                RegicideItems.instance().getLogger().warning("BYTE, BYTE_ARRAY, DOUBLE, FLOAT, INTEGER, INTEGER_ARRAY, SHORT, STRING");
            }
        }
    }
}
