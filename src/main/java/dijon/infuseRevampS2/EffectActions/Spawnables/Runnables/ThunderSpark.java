package dijon.infuseRevampS2.EffectActions.Spawnables.Runnables;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.apache.maven.model.License;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ThunderSpark extends BukkitRunnable {

    Player player;
    ArrayList<UUID> trustedList;

    HashMap<UUID, Integer> thunderHitCount = new HashMap<>();

    int runCount = 0;
    int timer = 0;

    int interval = 15;
    int maxRunCount = 10;

    public ThunderSpark(Player player) {
        this.player = player;
        trustedList = PlayerDataManager.getTrustedList(player.getUniqueId());
        runTaskTimer(InfuseRevampS2.instance, 0, 2);
        if(!player.getWorld().isClearWeather()){
            interval = 10;
            maxRunCount = 15;
        }
    }

    @Override
    public void run() {
        timer++;
        if(runCount >= maxRunCount) cancel();
        if(timer % interval == 0){
            for(Entity e : player.getNearbyEntities(10, 10, 10)){
                if(!(e instanceof LivingEntity livingEntity)) continue;
                if(e instanceof Villager) continue;
                if(trustedList.contains(e.getUniqueId())) continue;
                if(thunderHitCount.getOrDefault(livingEntity.getUniqueId(), 0) >= 3) continue;

                livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
                Helpers.trueDamage(livingEntity, 3);

                thunderHitCount.putIfAbsent(livingEntity.getUniqueId(), 0);
                thunderHitCount.put(livingEntity.getUniqueId(), thunderHitCount.get(livingEntity.getUniqueId()) + 1);
            }
            runCount++;
        }

        for(Entity e : player.getNearbyEntities(10, 10, 10)){
            if(!(e instanceof LivingEntity livingEntity)) continue;
            livingEntity.getWorld().spawnParticle(Particle.TRIAL_SPAWNER_DETECTION_OMINOUS, livingEntity.getLocation().add(0, 1, 0), 10, 0.25, 0.25, 0.25, 0.01);
        }

    }
}
