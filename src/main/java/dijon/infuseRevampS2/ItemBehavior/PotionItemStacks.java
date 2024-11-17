package dijon.infuseRevampS2.ItemBehavior;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.Color;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.HashMap;

public class PotionItemStacks {

    public final static HashMap<InfuseEffect, ArrayList<Component>> masterLoreList = new HashMap<>();

    public static void initializeLore(){
        ArrayList<Component> lore = new ArrayList<>();

        lore.add(Component.text("emerald wow"));
        masterLoreList.put(InfuseEffect.EMERALD, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("ENDER wow"));
        masterLoreList.put(InfuseEffect.ENDER, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("FEATHER wow"));
        masterLoreList.put(InfuseEffect.FEATHER, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("FIRE wow"));
        masterLoreList.put(InfuseEffect.FIRE, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("FROST wow"));
        masterLoreList.put(InfuseEffect.FROST, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("HASTE wow"));
        masterLoreList.put(InfuseEffect.HASTE, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("HEART wow"));
        masterLoreList.put(InfuseEffect.HEART, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("INVIS wow"));
        masterLoreList.put(InfuseEffect.INVIS, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("OCEAN wow"));
        masterLoreList.put(InfuseEffect.OCEAN, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("REGEN wow"));
        masterLoreList.put(InfuseEffect.REGEN, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("SPEED wow"));
        masterLoreList.put(InfuseEffect.SPEED, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("STRENGTH wow"));
        masterLoreList.put(InfuseEffect.STRENGTH, (ArrayList<Component>) lore.clone());
        lore.clear();

        lore.add(Component.text("THUNDER wow"));
        masterLoreList.put(InfuseEffect.THUNDER, (ArrayList<Component>) lore.clone());
        lore.clear();
    }

    public static ItemStack getItemStack(InfuseEffect infuseEffect){

        ItemStack potion = new ItemStack(Material.POTION);
        ItemMeta meta = potion.getItemMeta();
        PotionMeta potMeta = (PotionMeta) meta;

        String name = infuseEffect.getName();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);

        potMeta.displayName(Component.text(name + " Effect").color(infuseEffect.getColor()).decoration(TextDecoration.ITALIC, false));
        potMeta.lore(masterLoreList.get(infuseEffect));

        potMeta.getPersistentDataContainer().set(new NamespacedKey(InfuseRevampS2.instance, "InfuseData"), PersistentDataType.STRING, infuseEffect.getName());
        potMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        potMeta.addItemFlags(ItemFlag.HIDE_STORED_ENCHANTS);
        potMeta.addEnchant(Enchantment.BINDING_CURSE,1 , true);
        potMeta.setColor(Color.fromRGB(infuseEffect.getRawColor()));
        potion.setItemMeta(potMeta);

        return potion;
    }

}
