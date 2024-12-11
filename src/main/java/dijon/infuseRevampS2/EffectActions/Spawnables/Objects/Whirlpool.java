package dijon.infuseRevampS2.EffectActions.Spawnables.Objects;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import javax.swing.*;
import java.util.ArrayList;
import java.util.UUID;

public class Whirlpool extends BukkitRunnable {

    Location location;
    Player player;
    ArrayList<UUID> trustedList;

    double theta;
    double finalAmount;

    public Whirlpool(Location location, Player player) {
        this.location = location;
        this.player = player;
        finalAmount = InfuseEffect.OCEAN.getSparkDuration();
        trustedList = PlayerDataManager.getTrustedList(player.getUniqueId());
        runTaskTimer(InfuseRevampS2.instance, 0, 1);
    }

    @Override
    public void run() {
        location = player.getLocation();
        spawnAllParticles();

        for(Entity entity : location.getNearbyEntities(15, 15, 15)){

            if(entity.equals(player)) continue;
            if(trustedList.contains(entity.getUniqueId())) continue;

            Vector vecToEntity = location.clone().subtract(entity.getLocation()).toVector();
            if(vecToEntity.length() <= 5) continue;
            double force = 10 / vecToEntity.lengthSquared();
            vecToEntity.normalize();
            vecToEntity.multiply(force);
            entity.setVelocity(entity.getVelocity().add(vecToEntity));
        }

        theta += 0.05;
        if(theta > finalAmount) cancel();
    }

    public void spawnAllParticles(){
        spawnParticles(0);
        spawnParticles(1 * Math.PI/4);
        spawnParticles(2 * Math.PI/4);
        spawnParticles(3 * Math.PI/4);
        spawnParticles(4 * Math.PI/4);
        spawnParticles(5 * Math.PI/4);
        spawnParticles(6 * Math.PI/4);
        spawnParticles(7 * Math.PI/4);
        spawnParticles(8 * Math.PI/4);
    }

    public void spawnParticles(double offset){
        double x1 = Math.cos(theta + offset - 0.4);
        double z1 = Math.sin(theta + offset - 0.4);
        double x2 = Math.cos(theta + offset - 0.2);
        double z2 = Math.sin(theta + offset - 0.2);
        double x3 = Math.cos(theta + offset);
        double z3 = Math.sin(theta + offset);

        for(int i=5;i<=12;i+=3){
            Location newLoc1 = location.clone().add(new Vector(x1 * i, 1, z1 * i));
            Location newLoc2 = location.clone().add(new Vector(x2 * i, 2, z2 * i));
            Location newLoc3 = location.clone().add(new Vector(x3 * i, 3, z3 * i));
            newLoc1.getWorld().spawnParticle(Particle.RAIN, newLoc1, 10);
            newLoc2.getWorld().spawnParticle(Particle.RAIN, newLoc2, 10);
            newLoc3.getWorld().spawnParticle(Particle.RAIN, newLoc3, 10, 0, 0, 0, 0);
            offset -= 0.2;
            x1 = Math.cos(theta + offset - 0.4);
            z1 = Math.sin(theta + offset - 0.4);
            x2 = Math.cos(theta + offset - 0.2);
            z2 = Math.sin(theta + offset - 0.2);
            x3 = Math.cos(theta + offset);
            z3 = Math.sin(theta + offset);
        }
    }
}
