package dijon.infuseRevampS2.EffectActions.Spawnables.Objects;

import dijon.infuseRevampS2.InfuseRevampS2;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.*;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Transformation;
import org.joml.AxisAngle4f;
import org.joml.Vector3f;

import java.util.UUID;

public class HealthIndicator extends BukkitRunnable {

    Player player;
    UUID uuid;
    int timeLimit;
    int counter = 0;
    TextDisplay textDisplay;

    public HealthIndicator(Player player, int durationInSeconds, int frequency){

        this.player = player;
        this.uuid = player.getUniqueId();
        timeLimit = durationInSeconds * frequency;
        if(!player.getPassengers().isEmpty()) return;

        Transformation transformation = new Transformation(new Vector3f(0,0.5f,0), new AxisAngle4f(0,0,0,0), new Vector3f(1.2f, 1.2f, 1.2f), new AxisAngle4f(0,0,0,0));

        textDisplay = (TextDisplay) player.getWorld().spawnEntity(player.getLocation(), EntityType.TEXT_DISPLAY);

        int healthDisplay = (int) player.getHealth();

        textDisplay.text(Component.text(healthDisplay + " ❤").color(TextColor.color(255, 47, 51)).decorate(TextDecoration.BOLD));
        textDisplay.setBackgroundColor(Color.fromARGB(0, 255, 47, 51));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setShadowed(true);
        textDisplay.setTransformation(transformation);
        textDisplay.setVisibleByDefault(true);
        player.addPassenger(textDisplay);

        runTaskTimer(InfuseRevampS2.instance, 0, 20 / frequency);
    }

    @Override
    public void run() {

        int healthDisplay = (int) player.getHealth();
        textDisplay.text(Component.text(healthDisplay + " ❤").color(TextColor.color(255, 47, 51)).decorate(TextDecoration.BOLD));

        counter++;
        if(counter >= timeLimit || Bukkit.getPlayer(uuid) == null){
            textDisplay.remove();
            cancel();
        }

    }
}
