package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
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

public class RegenListener implements Listener {

    public RegenListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.playerAndMob(e)) return;
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        if(sparked(e.getDamager().getUniqueId())) return;
        Player player = (Player) e.getDamager();
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;
        if(e.getItem().getType().equals(Material.POTION)) return;
        float additionalSaturation = 6.0f;
        float newSaturation = Math.min(e.getPlayer().getSaturation() + additionalSaturation, 20.0f);
        e.getPlayer().setSaturation(newSaturation);
    }

    @EventHandler
    public void onAttackSpark(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player player)) return;
        if(!sparked(player.getUniqueId())) return;

        double healAmount = 0;

        if(e.getEntity() instanceof Player){
            healAmount = e.getFinalDamage();
        }

        player.heal(healAmount);
        for(UUID uuid : PlayerDataManager.getTrustedList(player.getUniqueId())){
            Player teammate = Bukkit.getPlayer(uuid);
            if(teammate == null) continue;
            teammate.heal(healAmount);
        }
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.REGEN);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.REGEN);
    }

}