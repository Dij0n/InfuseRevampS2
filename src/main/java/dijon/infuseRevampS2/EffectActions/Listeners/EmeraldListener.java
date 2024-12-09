package dijon.infuseRevampS2.EffectActions.Listeners;

import com.destroystokyo.paper.event.player.PlayerLaunchProjectileEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentOffer;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class EmeraldListener implements Listener {

    public EmeraldListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void emeraldXp(PlayerExpChangeEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(hasEffect(uuid)){
            if(e.getAmount() > 0){
                if(sparked(uuid)){
                    e.setAmount(e.getAmount() * 3);
                }else{
                    e.setAmount((int) (e.getAmount() * 1.5));
                }
            }
        }
    }

    @EventHandler
    public void emeraldXpMend(PlayerItemMendEvent e){
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(hasEffect(uuid)){
            if(sparked(uuid)){
                e.setRepairAmount(e.getRepairAmount() * 3);
            }else{
                e.setRepairAmount((int) (e.getRepairAmount() * 1.5));
            }
        }
    }

    @EventHandler
    public void emeraldConsumeItem(PlayerItemConsumeEvent e){
        Player p = e.getPlayer();
        if(e.getItem().getType().equals(Material.POTION)) return;
        if(getConsumeChance(p)){
            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            ItemStack lykainosTheDestroyer = e.getItem().clone();
            lykainosTheDestroyer.setAmount(1);
            p.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, p.getLocation().add(0, 1, 0), 10, 0.25, 0.25, 0.25);
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().getInventory().addItem(lykainosTheDestroyer);
            }, 1);
        }
    }

    @EventHandler
    public void emeraldConsumeProj(PlayerLaunchProjectileEvent e){
        Player p = e.getPlayer();
        if(e.getProjectile().getType().equals(EntityType.TRIDENT)) return;
        if(getConsumeChance(p)){
            p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 10, 1);
            ItemStack lykainosTheDestroyer = e.getItemStack().clone();
            lykainosTheDestroyer.setAmount(1);
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().getInventory().addItem(lykainosTheDestroyer);
            }, 1);
        }
    }

    @EventHandler
    public void emeraldConsumeXP(PlayerPickupExperienceEvent e){
        Player p = e.getPlayer();
        if(getConsumeChance(p)){
            p.giveExp(e.getExperienceOrb().getExperience());
            e.setCancelled(true);
        }
    }

    public boolean getConsumeChance(Player p){
        UUID uuid = p.getUniqueId();
        double chance;
        if(hasEffect(uuid)){
            if(sparked(uuid)){
                chance = 0.5;
            }else{
                chance = 0.25;
            }
            double random = Math.random();
            return random < chance;
        }
        return false;
    }

    @EventHandler
    public void onEnchant(PrepareItemEnchantEvent e){
        if(!hasEffect(e.getEnchanter().getUniqueId())) return;
        for(EnchantmentOffer offer : e.getOffers()){
            if(offer != null){
                offer.setCost(30);
            }
        }
    }

    @EventHandler
    public void onHoldSword(PlayerItemHeldEvent e){
        Helpers.onHoldSpecialItem(e, InfuseEffect.EMERALD, Helpers.swords, Enchantment.LOOTING, 5);
        Helpers.onNotHoldSpecialItem(e, Helpers.swords, Enchantment.LOOTING, 5);
    }

    @EventHandler
    public void onDropSword(PlayerDropItemEvent e){
        Helpers.onDropSpecialItem(e, Helpers.swords, Enchantment.LOOTING, 5);
    }

    @EventHandler
    public void onMoveSword(InventoryClickEvent e){
        Helpers.onMoveSpecialItem(e, Helpers.swords, Enchantment.LOOTING, 5);
    }

    //HELPERS

    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.EMERALD);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.EMERALD);
    }


}
