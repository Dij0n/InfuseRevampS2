package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Spawnables.Objects.HealthIndicator;
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

import java.util.HashMap;
import java.util.UUID;

public class HeartListener implements Listener {

    public static final HashMap<UUID, HealthIndicator> indicatorHashMap = new HashMap<>();

    public HeartListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(Helpers.playerAndMob(e)){
            if(!hasEffect(e.getDamager().getUniqueId())) return;
            int hitCount = PlayerDataManager.getHitCount(e.getDamager().getUniqueId());
            if(hitCount % 10 == 0){
                HealthIndicator healthIndicator = new HealthIndicator((LivingEntity) e.getEntity(), 3, 10);
                indicatorHashMap.put(e.getEntity().getUniqueId(), healthIndicator);
            }
        }
    }

    @EventHandler
    public void onEatApple(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.POTION)) return;
        if(!hasEffect(e.getPlayer().getUniqueId())) return;

        e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 0));

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
