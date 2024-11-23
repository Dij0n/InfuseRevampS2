package dijon.infuseRevampS2.EffectActions.Listeners.Helpers;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashSet;
import java.util.Set;

public class ListenerHelpers {

    public static final Set<Material> swords = new HashSet<>();
    public static final Set<Material> pickaxesAndTools = new HashSet<>();
    public static final Set<Material> bows = new HashSet<>();
    public static final Set<Material> veinMined = new HashSet<>();

    public static void initialize(){

        swords.add(Material.WOODEN_SWORD);
        swords.add(Material.STONE_SWORD);
        swords.add(Material.IRON_SWORD);
        swords.add(Material.GOLDEN_SWORD);
        swords.add(Material.DIAMOND_SWORD);
        swords.add(Material.NETHERITE_SWORD);

        pickaxesAndTools.add(Material.WOODEN_PICKAXE);
        pickaxesAndTools.add(Material.STONE_PICKAXE);
        pickaxesAndTools.add(Material.IRON_PICKAXE);
        pickaxesAndTools.add(Material.GOLDEN_PICKAXE);
        pickaxesAndTools.add(Material.DIAMOND_PICKAXE);
        pickaxesAndTools.add(Material.NETHERITE_PICKAXE);
        pickaxesAndTools.add(Material.WOODEN_SHOVEL);
        pickaxesAndTools.add(Material.STONE_SHOVEL);
        pickaxesAndTools.add(Material.IRON_SHOVEL);
        pickaxesAndTools.add(Material.GOLDEN_SHOVEL);
        pickaxesAndTools.add(Material.DIAMOND_SHOVEL);
        pickaxesAndTools.add(Material.NETHERITE_SHOVEL);
        pickaxesAndTools.add(Material.WOODEN_HOE);
        pickaxesAndTools.add(Material.STONE_HOE);
        pickaxesAndTools.add(Material.IRON_HOE);
        pickaxesAndTools.add(Material.GOLDEN_HOE);
        pickaxesAndTools.add(Material.DIAMOND_HOE);
        pickaxesAndTools.add(Material.NETHERITE_HOE);
        pickaxesAndTools.add(Material.WOODEN_AXE);
        pickaxesAndTools.add(Material.STONE_AXE);
        pickaxesAndTools.add(Material.IRON_AXE);
        pickaxesAndTools.add(Material.GOLDEN_AXE);
        pickaxesAndTools.add(Material.DIAMOND_AXE);
        pickaxesAndTools.add(Material.NETHERITE_AXE);

        bows.add(Material.BOW);
        bows.add(Material.CROSSBOW);
        bows.add(Material.TRIDENT);

        veinMined.add(Material.COAL_ORE);
        veinMined.add(Material.COPPER_ORE);
        veinMined.add(Material.IRON_ORE);
        veinMined.add(Material.GOLD_ORE);
        veinMined.add(Material.REDSTONE_ORE);
        veinMined.add(Material.LAPIS_ORE);
        veinMined.add(Material.EMERALD_ORE);
        veinMined.add(Material.DIAMOND_ORE);
        veinMined.add(Material.DEEPSLATE_COAL_ORE);
        veinMined.add(Material.DEEPSLATE_COPPER_ORE);
        veinMined.add(Material.DEEPSLATE_IRON_ORE);
        veinMined.add(Material.DEEPSLATE_GOLD_ORE);
        veinMined.add(Material.DEEPSLATE_REDSTONE_ORE);
        veinMined.add(Material.DEEPSLATE_LAPIS_ORE);
        veinMined.add(Material.DEEPSLATE_EMERALD_ORE);
        veinMined.add(Material.DEEPSLATE_DIAMOND_ORE);
        veinMined.add(Material.NETHER_QUARTZ_ORE);
        veinMined.add(Material.NETHER_GOLD_ORE);
    }

    //Special Enchanting

    public static void onHoldSpecialItem(PlayerItemHeldEvent e, InfuseEffect effect, Set<Material> materials, Enchantment enchantment, int specialLevel){
        Player player = e.getPlayer();
        if(!PlayerDataManager.hasEffect(player.getUniqueId(), effect)) return;
        if(player.getInventory().getItem(e.getNewSlot()) == null) return;

        ItemStack newItemStack = player.getInventory().getItem(e.getNewSlot());

        if(materials.contains(newItemStack.getType())){
            if(newItemStack.containsEnchantment(enchantment)){
                ItemMeta meta = newItemStack.getItemMeta();
                meta.getPersistentDataContainer().set(enchantment.getKey(), PersistentDataType.INTEGER, newItemStack.getEnchantmentLevel(enchantment));
                newItemStack.setItemMeta(meta);
            }
            newItemStack.addUnsafeEnchantment(enchantment, specialLevel);
        }
    }
    public static void onNotHoldSpecialItem(PlayerItemHeldEvent e, Set<Material> materials, Enchantment enchantment, int specialLevel){
        Player player = e.getPlayer();
        if(player.getInventory().getItem(e.getPreviousSlot()) == null) return;

        ItemStack oldItemStack = player.getInventory().getItem(e.getPreviousSlot());

        generalRemoveSpecialItem(oldItemStack, materials, enchantment, specialLevel);
    }
    public static void onDropSpecialItem(PlayerDropItemEvent e, Set<Material> materials, Enchantment enchantment, int specialLevel){
        ItemStack itemStack = e.getItemDrop().getItemStack();
        generalRemoveSpecialItem(itemStack, materials, enchantment, specialLevel);
    }
    public static void onMoveSpecialItem(InventoryClickEvent e, Set<Material> materials, Enchantment enchantment, int specialLevel){
        ItemStack itemStack = e.getCursor();
        generalRemoveSpecialItem(e.getCursor(), materials, enchantment, specialLevel);
        generalRemoveSpecialItem(e.getCurrentItem(), materials, enchantment, specialLevel);
    }
    public static void generalRemoveSpecialItem(ItemStack itemStack, Set<Material> materials, Enchantment enchantment, int specialLevel){
        if(itemStack == null) return;
        if(!materials.contains(itemStack.getType())) return;
        if(!itemStack.containsEnchantment(enchantment)) return;
        if(itemStack.getEnchantmentLevel(enchantment) != specialLevel) return;
        if(itemStack.getPersistentDataContainer().has(enchantment.getKey())){
            if(itemStack.getEnchantmentLevel(enchantment) == itemStack.getPersistentDataContainer().get(enchantment.getKey(), PersistentDataType.INTEGER)) return;
        }


        itemStack.removeEnchantment(enchantment);
        if(itemStack.getPersistentDataContainer().has(enchantment.getKey())){
            itemStack.addUnsafeEnchantment(enchantment, itemStack.getPersistentDataContainer().get(enchantment.getKey(), PersistentDataType.INTEGER));
        }
    }



    //Boolean Checks
    public static boolean bothPlayers(EntityDamageByEntityEvent e){
        return e.getEntity() instanceof Player && e.getDamager() instanceof Player;
    }
    public static boolean playerAndMob(EntityDamageByEntityEvent e){
        return e.getEntity() instanceof LivingEntity && e.getDamager() instanceof Player;
    }

    public static boolean mobAndPlayer(EntityDamageByEntityEvent e){
        return e.getEntity() instanceof Player && e.getDamager() instanceof LivingEntity;
    }


}
