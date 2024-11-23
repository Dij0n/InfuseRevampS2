package dijon.infuseRevampS2.EffectActions.Particles;

import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class InvisParticles extends BukkitRunnable {

    Player player;
    UUID uuid;
    ArrayList<Player> trusted = new ArrayList<>();
    int timeLimit;
    int counter = 0;

    public InvisParticles(Player player, ArrayList<UUID> trustedUUIDs, int durationInSeconds, int frequency){

        this.player = player;
        this.uuid = player.getUniqueId();

        for(UUID uuid : trustedUUIDs){
            if(Bukkit.getPlayer(uuid) != null){
                trusted.add(Bukkit.getPlayer(uuid));
            }
        }

        timeLimit = durationInSeconds * frequency;

        runTaskTimer(InfuseRevampS2.instance, 0, 20 / frequency);
    }

    @Override
    public void run() {

        int size = 10;
        int stepSize = 2;

        for(double x = player.getLocation().getX() - size; x < player.getLocation().getX() + size + 1; x += (Math.random() * stepSize)){
            for(double y = player.getLocation().getY() - 1; y < player.getLocation().getY() + 3 + (Math.random() * 3); y += Math.random()){
                for(double z = player.getLocation().getZ() - size; z < player.getLocation().getZ() + size + 1; z += (Math.random() * stepSize)){
                    player.getWorld().spawnParticle(Particle.CAMPFIRE_COSY_SMOKE, new Location(player.getWorld(), x, y, z), 1, 0, 0, 0, 0.02);
                }
            }
        }

        counter++;
        if(counter >= timeLimit || Bukkit.getPlayer(uuid) == null){
            cancel();
        }

    }
}
