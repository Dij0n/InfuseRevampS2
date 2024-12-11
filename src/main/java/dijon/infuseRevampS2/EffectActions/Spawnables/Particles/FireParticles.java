package dijon.infuseRevampS2.EffectActions.Spawnables.Particles;

import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class FireParticles extends BukkitRunnable {

    Player player;
    UUID uuid;
    int timeLimit;
    int counter = 0;
    int theta = 0;

    public FireParticles(Player player, int durationInSeconds, int frequency){

        this.player = player;
        this.uuid = player.getUniqueId();

        timeLimit = durationInSeconds * frequency;

        runTaskTimer(InfuseRevampS2.instance, 0, 20 / frequency);
    }

    @Override
    public void run() {

        World world = player.getWorld();
        double x;
        double z;
        double theta = 0;
        int radius = 5;

        while(theta <= Math.PI * 4){
            x = Math.sin(theta) * radius;
            z = Math.cos(theta) * radius;
            Location circleLoc = new Location(world, player.getLocation().getX() + x, player.getLocation().getY(), player.getLocation().getZ() + z);
            world.spawnParticle(Particle.TRIAL_SPAWNER_DETECTION, circleLoc, 2, 0.1, 0.5, 0.1, 0.01);
            theta += 0.1D;
        }

        counter++;
        if(counter >= timeLimit || Bukkit.getPlayer(uuid) == null){
            cancel();
        }

    }


}
