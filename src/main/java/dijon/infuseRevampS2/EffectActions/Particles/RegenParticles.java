package dijon.infuseRevampS2.EffectActions.Particles;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.UUID;

public class RegenParticles extends BukkitRunnable {

    Player player;
    UUID uuid;
    ArrayList<Player> trusted = new ArrayList<>();
    int timeLimit;
    int counter = 0;

    public RegenParticles(Player player, ArrayList<UUID> trustedUUIDs, int durationInSeconds, int frequency){

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
        for(Player teammate : trusted){
            if(!player.getWorld().equals(teammate.getWorld())) continue;
            if(player.getLocation().distance(teammate.getLocation()) <= 24){
                drawLine(player.getLocation().add(new Vector(0, 1, 0)), teammate.getLocation().add(new Vector(0, 1, 0)));
            }
        }

        counter++;
        if(counter >= timeLimit || Bukkit.getPlayer(uuid) == null){
            cancel();
        }

    }

    public void drawLine(Location target, Location source){
        Vector totalDistance = target.toVector().subtract(source.toVector());
        Vector directionOfTravel = totalDistance.clone();
        directionOfTravel.normalize().multiply(0.5);

        Location pen = source.clone();

        while (pen.distance(source) < target.distance(source)){
            double progress = pen.distance(source) / target.distance(source);
            pen.getWorld().spawnParticle(Particle.DUST, pen, 2, new Particle.DustOptions(getProgressWhiteToPink(progress), 1));
            pen.add(directionOfTravel);
        }
    }

    public Color getProgressWhiteToPink(double prog){
        double progress = Math.max(0, Math.min(1, prog));
        progress = 1 - progress;

        // RGB values for white (255, 255, 255)
        int startRed = 255;
        int startGreen = 255;
        int startBlue = 255;

        // RGB values for pink (255, 192, 203)
        int endRed = 255;
        int endGreen = 0;
        int endBlue = 224;

        // Interpolate between white and pink based on progress
        int red = (int) (startRed + (endRed - startRed) * progress);
        int green = (int) (startGreen + (endGreen - startGreen) * progress);
        int blue = (int) (startBlue + (endBlue - startBlue) * progress);

        return Color.fromRGB(red, green, blue);
    }
}
