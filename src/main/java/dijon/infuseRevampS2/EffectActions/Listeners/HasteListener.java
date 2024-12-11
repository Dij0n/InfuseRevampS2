package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.SmeltingValues;
import dijon.infuseRevampS2.InfuseRevampS2;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class HasteListener implements Listener {

    public HasteListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    //PASSIVES
    //SHIELD COOLDOWN -> 2.5 SECONDS
    @EventHandler
    public void onShieldAttack(PlayerShieldDisableEvent e){
        if(hasEffect(e.getPlayer().getUniqueId())){
            e.setCooldown(e.getCooldown() / 2);
        }
    }

    //VEIN MINER
    @EventHandler
    public void onVeinMine(BlockBreakEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;
        boolean hasFireEffect = PlayerDataManager.hasEffect(e.getPlayer().getUniqueId(), InfuseEffect.FIRE);
        if(!e.getPlayer().isSneaking()) return;
        int boxSize = 4; //9 x 9 x 9 cube
        Material originalBlock = e.getBlock().getType();
        if(Helpers.veinMined.contains(e.getBlock().getType())){
            for(int x = e.getBlock().getX() - boxSize; x < e.getBlock().getX() + boxSize + 1; x++){
                for(int y = e.getBlock().getY() - boxSize; y < e.getBlock().getY() + boxSize + 1; y++){
                    for(int z = e.getBlock().getZ() - boxSize; z < e.getBlock().getZ() + boxSize + 1; z++){
                        Block block = e.getBlock().getWorld().getBlockAt(x, y, z);
                        if(originalBlock.equals(block.getType())){
                            for (ItemStack itemStack : block.getDrops(e.getPlayer().getInventory().getItemInMainHand())){
                                if(hasFireEffect){
                                    if(SmeltingValues.isSmeltable(itemStack.getType())){
                                        itemStack.setType(SmeltingValues.getSmelted(itemStack.getType()));
                                        e.getPlayer().getWorld().playSound(block.getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.1f, 1);
                                    }
                                }
                                e.getPlayer().getWorld().dropItemNaturally(block.getLocation(), itemStack);
                            }

                            block.breakNaturally(new ItemStack(Material.STICK));
                        }
                    }
                }
            }
        }
    }

    //-1 DAMAGE
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!Helpers.playerAndMob(e)) return;
        if(!sparked(e.getDamager().getUniqueId())) return;

        e.setDamage(Math.max(e.getDamage() - 1, 0));
    }

    @EventHandler
    public void onHoldPick(PlayerItemHeldEvent e){
        Helpers.onHoldSpecialItem(e, InfuseEffect.HASTE, Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.onHoldSpecialItem(e, InfuseEffect.HASTE, Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.onHoldSpecialItem(e, InfuseEffect.HASTE, Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
        Helpers.onNotHoldSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.onNotHoldSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.onNotHoldSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    @EventHandler
    public void onDropPick(PlayerDropItemEvent e){
        Helpers.onDropSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.onDropSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.onDropSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    @EventHandler
    public void onMovePick(InventoryClickEvent e){
        Helpers.onMoveSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.onMoveSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.onMoveSpecialItem(e, Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.HASTE);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.HASTE);
    }

}
