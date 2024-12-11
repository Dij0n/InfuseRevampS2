package dijon.infuseRevampS2.HUD;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PassiveParticles extends BukkitRunnable {
    @Override
    public void run() {
        for(Player player : Bukkit.getOnlinePlayers()){
            spawnParticles(player, PlayerDataManager.getPrimary(player.getUniqueId()));
            spawnParticles(player, PlayerDataManager.getSecondary(player.getUniqueId()));
        }
    }


    public void spawnParticles(Player player, InfuseEffect effect){
        if(effect == InfuseEffect.NONE) return;

        Color color = Color.fromRGB(effect.getRawColor());

        player.spawnParticle(Particle.ENTITY_EFFECT, player.getLocation(), 1, 0.2, 0.2, 0.2, 1, color);
    }

}
