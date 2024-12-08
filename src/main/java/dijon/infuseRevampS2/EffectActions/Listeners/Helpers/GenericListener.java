package dijon.infuseRevampS2.EffectActions.Listeners.Helpers;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
    public void onWitherHit(EntityDamageEvent e){
//        if(!(e.getEntity() instanceof Player player)) return;
//        if(e.getCause().equals(EntityDamageEvent.DamageCause.WITHER)){
//            e.setCancelled(true);
//        }
    }



}
