package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.EffectActions.Particles.HealthIndicator;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class HeartListener implements Listener {

    public HeartListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(ListenerHelpers.bothPlayers(e)){
            if(!hasEffect(e.getDamager().getUniqueId())) return;
            int hitCount = PlayerDataManager.getHitCount(e.getDamager().getUniqueId());
            if(hitCount % 10 == 0){
                new HealthIndicator((Player) e.getEntity(), 3, 10);
            }
        }
    }

    @EventHandler
    public void onEatApple(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.GOLDEN_APPLE) && hasEffect(e.getPlayer().getUniqueId())){
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1));
        }
        if(e.getItem().getType().equals(Material.ENCHANTED_GOLDEN_APPLE) && hasEffect(e.getPlayer().getUniqueId())){
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 4));
        }
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.HEART);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.HEART);
    }

}
