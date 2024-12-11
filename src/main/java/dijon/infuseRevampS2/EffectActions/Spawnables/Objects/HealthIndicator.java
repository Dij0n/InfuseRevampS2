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

    LivingEntity livingEntity;
    UUID uuid;
    int timeLimit;
    int counter = 0;
    TextDisplay textDisplay;

    public HealthIndicator(LivingEntity livingEntity, int durationInSeconds, int frequency){

        this.livingEntity = livingEntity;
        this.uuid = livingEntity.getUniqueId();
        timeLimit = durationInSeconds * frequency;
        if(!livingEntity.getPassengers().isEmpty()) return;

        Transformation transformation = new Transformation(new Vector3f(0,0.5f,0), new AxisAngle4f(0,0,0,0), new Vector3f(1.2f, 1.2f, 1.2f), new AxisAngle4f(0,0,0,0));

        textDisplay = (TextDisplay) livingEntity.getWorld().spawnEntity(livingEntity.getLocation(), EntityType.TEXT_DISPLAY);

        int healthDisplay = (int) (livingEntity.getHealth() + livingEntity.getAbsorptionAmount());
        double heathDisplayFull = (double) healthDisplay / 2;
        Component name = Component.text(heathDisplayFull).color(TextColor.color(255, 47, 51)).decorate(TextDecoration.BOLD);
        Component heart = Component.text(" ❤").color(TextColor.color(255, 47, 51)).decoration(TextDecoration.BOLD, false);
        if(livingEntity.getAbsorptionAmount() > 0){
            name = Component.text(heathDisplayFull).color(TextColor.color(255, 255, 0)).decorate(TextDecoration.BOLD);
            heart = Component.text(" ❤").color(TextColor.color(255, 255, 0)).decoration(TextDecoration.BOLD, false);
        }
        textDisplay.text(name.append(heart));

        textDisplay.setBackgroundColor(Color.fromARGB(0, 255, 47, 51));
        textDisplay.setBillboard(Display.Billboard.CENTER);
        textDisplay.setShadowed(true);
        textDisplay.setTransformation(transformation);
        textDisplay.setVisibleByDefault(true);
        livingEntity.addPassenger(textDisplay);

        runTaskTimer(InfuseRevampS2.instance, 0, 20 / frequency);
    }

    @Override
    public void run() {

        if(livingEntity == null){
            textDisplay.remove();
            cancel();
        }

        int healthDisplay = (int) (livingEntity.getHealth() + livingEntity.getAbsorptionAmount());
        double heathDisplayFull = (double) healthDisplay / 2;
        Component name = Component.text(heathDisplayFull).color(TextColor.color(255, 47, 51)).decorate(TextDecoration.BOLD);
        Component heart = Component.text(" ❤").color(TextColor.color(255, 47, 51)).decoration(TextDecoration.BOLD, false);
        if(livingEntity.getAbsorptionAmount() > 0 && healthDisplay != 10){
            name = Component.text(heathDisplayFull).color(TextColor.color(255, 255, 0)).decorate(TextDecoration.BOLD);
            heart = Component.text(" ❤").color(TextColor.color(255, 255, 0)).decoration(TextDecoration.BOLD, false);
        }
        textDisplay.text(name.append(heart));

        counter++;
        if(counter >= timeLimit || livingEntity.isDead()){
            textDisplay.remove();
            cancel();
        }
    }

    public void die(){
        textDisplay.remove();
    }
}
