package dijon.infuseRevampS2.EffectActions.Spawnables.Runnables;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.apache.maven.model.License;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
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
        if(timer % (interval / 2) == 0){
            thunderParticles(player);
        }
        timer++;

    }

    public void thunderParticles (Player p){
        World world = p.getWorld();
        double x;
        double z;
        double theta = 0;
        int radius = 10;

        while(theta <= Math.PI * 4){
            x = Math.sin(theta) * radius;
            z = Math.cos(theta) * radius;
            int randomVal = (int) (Math.floor(Math.random() * 4));
            Particle.DustOptions dustOptions1 = new Particle.DustOptions(Color.fromRGB(255, 231, 25), 3);
            Particle.DustOptions dustOptions2 = new Particle.DustOptions(Color.fromRGB(255, 225, 25), 2);
            Particle.DustOptions dustOptions3 = new Particle.DustOptions(Color.fromRGB(171, 171, 171), 2);
            Particle.DustOptions dustOptions4 = new Particle.DustOptions(Color.fromRGB(53, 53, 53), 2);
            ArrayList<Particle.DustOptions> imtired = new ArrayList<>();
            imtired.add(dustOptions1);
            imtired.add(dustOptions2);
            imtired.add(dustOptions3);
            imtired.add(dustOptions4);
            Location circleLoc = new Location(world, p.getLocation().getX() + x, p.getLocation().getY(), p.getLocation().getZ() + z);
            world.spawnParticle(Particle.DUST, circleLoc, 5, imtired.get(randomVal));
            theta += 0.5D;
        }
    }
}
