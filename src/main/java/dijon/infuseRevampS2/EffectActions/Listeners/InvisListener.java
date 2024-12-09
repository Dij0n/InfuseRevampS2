package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import io.papermc.paper.event.entity.WardenAngerChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class InvisListener implements Listener {

    public InvisListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!Helpers.tenthHit(e)) return;
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        LivingEntity victim = (LivingEntity) e.getEntity();
        victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0));
        victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, 0));
    }


    @EventHandler
    public void bowShot(ProjectileHitEvent e){
        if(!(e.getHitEntity() instanceof Player victim)) return;

        Projectile projectile = e.getEntity();
        if(!(projectile instanceof Arrow arrow)) return;
        if(!(arrow.getShooter() instanceof Player player)) return;

        if(hasEffect(player.getUniqueId())){
            victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
        }
    }

    @EventHandler
    public void invisMobAvoider(EntityTargetEvent e){
        if(!(e.getTarget() instanceof Player player)) return;
        if(hasEffect(player.getUniqueId())){
            e.setCancelled(true);
        }

    }
    @EventHandler
    public void invisWardenAvoider(WardenAngerChangeEvent e){
        if(!(e.getTarget() instanceof Player player)) return;
        if(hasEffect(player.getUniqueId())){
            e.setNewAnger(0);
            e.setCancelled(true);
        }
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.INVIS);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.INVIS);
    }

}
