package dijon.infuseRevampS2.EffectActions.Listeners.Helpers;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class GenericListener implements Listener {

    public GenericListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttackPlayer(EntityDamageByEntityEvent e){
        if(Helpers.playerAndMob(e)){
            if(e.getEntity() instanceof Player victim && victim.isBlocking()){
                return;
            }
            if(e.getDamager() instanceof Player attacker && attacker.getAttackCooldown() < 1){
                return;
            }
            PlayerDataManager.incHitCount(e.getDamager().getUniqueId());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if(event.getEntity() instanceof Player){
            if (event.getCause() == EntityDamageEvent.DamageCause.LIGHTNING) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEatGApple(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE)){
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
            }, 1);
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0, true));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1, true));
            }, 2);
        }
        if(e.getItem().getType().equals(Material.ENCHANTED_GOLDEN_APPLE)){
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().removePotionEffect(PotionEffectType.ABSORPTION);
                e.getPlayer().removePotionEffect(PotionEffectType.REGENERATION);
                e.getPlayer().removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
                e.getPlayer().removePotionEffect(PotionEffectType.RESISTANCE);
            }, 1);
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 3, true));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1, true));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 6000, 0, true));
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 6000, 0, true));
            }, 2);

        }
    }

    @EventHandler
    public void onWitherHit(EntityDamageEvent e){
//        if(!(e.getEntity() instanceof Player player)) return;
//        if(e.getCause().equals(EntityDamageEvent.DamageCause.WITHER)){
//            e.setCancelled(true);
//        }
    }



}
