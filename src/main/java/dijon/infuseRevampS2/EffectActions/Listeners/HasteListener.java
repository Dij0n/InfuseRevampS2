package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;

import java.util.UUID;

public class HasteListener implements Listener {

    public HasteListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    //PASSIVES
    //SHIELD COOLDOWN -> 2.5 SECONDS
    @EventHandler
    public void onShieldAttack(PlayerShieldDisableEvent e){
        if(sparked(e.getPlayer().getUniqueId())){
            e.setCooldown(e.getCooldown() / 2);
        }
    }

    //VEIN MINER
    @EventHandler
    public void onVeinMine(BlockBreakEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;
        if(!e.getPlayer().isSneaking()) return;
        int boxSize = 4; //9 x 9 x 9 cube
        Material originalBlock = e.getBlock().getType();
        if(ListenerHelpers.veinMined.contains(e.getBlock().getType())){
            for(int x = e.getBlock().getX() - boxSize; x < e.getBlock().getX() + boxSize + 1; x++){
                for(int y = e.getBlock().getY() - boxSize; y < e.getBlock().getY() + boxSize + 1; y++){
                    for(int z = e.getBlock().getZ() - boxSize; z < e.getBlock().getZ() + boxSize + 1; z++){
                        Block block = e.getBlock().getWorld().getBlockAt(x, y, z);
                        if(originalBlock.equals(block.getType())){
                            block.breakNaturally(e.getPlayer().getInventory().getItemInMainHand());
                        }
                    }
                }
            }
        }
    }

    //-1 DAMAGE
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.bothPlayers(e)) return;
        if(!sparked(e.getDamager().getUniqueId())) return;

        e.setDamage(Math.min(e.getDamage() - 1, 0));
    }

    @EventHandler
    public void onHoldPick(PlayerItemHeldEvent e){
        ListenerHelpers.onHoldSpecialItem(e, InfuseEffect.HASTE, ListenerHelpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        ListenerHelpers.onHoldSpecialItem(e, InfuseEffect.HASTE, ListenerHelpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        ListenerHelpers.onHoldSpecialItem(e, InfuseEffect.HASTE, ListenerHelpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
        ListenerHelpers.onNotHoldSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        ListenerHelpers.onNotHoldSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        ListenerHelpers.onNotHoldSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    @EventHandler
    public void onDropPick(PlayerDropItemEvent e){
        ListenerHelpers.onDropSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        ListenerHelpers.onDropSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        ListenerHelpers.onDropSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    @EventHandler
    public void onMovePick(InventoryClickEvent e){
        ListenerHelpers.onMoveSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        ListenerHelpers.onMoveSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        ListenerHelpers.onMoveSpecialItem(e, ListenerHelpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.HASTE);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.HASTE);
    }

}
