package org.regicide.regicideitems.entities.items;
import jdk.internal.util.ByteArray;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.codehaus.plexus.interpolation.fixed.ObjectBasedValueSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.regicide.regicideitems.RegicideItems;
import org.regicide.regicideitems.config.ConfigManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
        for (String itemStrCustomID : items) {

            
            //Fields of itemStrCustomID
            String materialItemName = customItems.getString(itemStrCustomID+".type");

            //Meta
            String nmitem = customItems.getString(itemStrCustomID+".meta.name");
            ItemStack newitem = new ItemStack(Material.getMaterial(materialItemName));
            ItemMeta meta = newitem.getItemMeta();
            List<Component> loreitem = new ArrayList<>();
            setPersistentData(itemStrCustomID,meta,"STRING","ID",itemStrCustomID);
            for (String str : customItems.getStringList(itemStrCustomID+".meta.lore"))
                loreitem.add(Component.text(str).toBuilder().build());
            if (customItems.contains(itemStrCustomID+".meta.persistent-data") ) {
                ConfigurationSection persistentDataBlock = customConfigItems.getConfigurationSection("custom-items."+itemStrCustomID+
                        ".meta.persistent-data");
                Set<String> persistentDataPiece = persistentDataBlock.getKeys(false);
                for(String persistentDataa : persistentDataPiece){
                    String dk = persistentDataBlock.getString(persistentDataa+".data-key");
                    String dt = persistentDataBlock.getString(persistentDataa+".data-type");
                    Object dv = persistentDataBlock.getObject(persistentDataa+".data-value",Object.class);
                    setPersistentData(itemStrCustomID,meta,dt,dk,dv);
                }
            }

            
            meta.displayName(Component.text(nmitem).toBuilder().build());
            meta.lore(loreitem);
            newitem.setItemMeta(meta);
            CUSTOM_ITEM_MAP.put(itemStrCustomID,newitem);
            i++;

        }
    }

    /**
     *
     * @param itemStrCustomID String Item's Id
     * @param meta Meta Item's
     * @param dt DataType
     * @param dk DataKey
     * @param dv DataValue
     */
    private static void setPersistentData(@NotNull String itemStrCustomID, ItemMeta meta,String dt,String dk,Object dv){
        PersistentDataType persistentDataType;
        NamespacedKey nms = new NamespacedKey(RegicideItems.instance(),dk);
        switch (dt) {
            case "BYTE" : {
                persistentDataType = PersistentDataType.BYTE;
                Byte dataValue = (Byte) dv;
                meta.getPersistentDataContainer().set(nms,persistentDataType,dataValue);
                break;
            }
            case "BYTE-ARRAY" : {persistentDataType = PersistentDataType.BYTE_ARRAY;
                byte[] dataValue =(byte[]) dv;
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
                RegicideItems.instance().getLogger().warning("Field \"data-type\" for the item \"" + itemStrCustomID + "\" is set incorrectly!");
                RegicideItems.instance().getLogger().warning("Here is a list of acceptable values for this field:");
                RegicideItems.instance().getLogger().warning("BYTE, BYTE_ARRAY, DOUBLE, FLOAT, INTEGER, INTEGER_ARRAY, SHORT, STRING");
            }
        }
    }
    //public static byte[] convertObjectToBytes(Object obj) {
       // ByteArrayOutputStream boas = new ByteArrayOutputStream();
       // try (ObjectOutputStream ois = new ObjectOutputStream(boas)) {
       //     ois.writeObject(obj);
       //     return boas.toByteArray();
      //  } catch (IOException ioe) {
       //     ioe.printStackTrace();
      //  }
     //  throw new RuntimeException();
   // }



}

