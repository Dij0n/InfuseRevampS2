package dijon.infuseRevampS2.Data;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class CooldownFileManager {

    public final static HashMap<InfuseEffect, Integer> masterDurationList = new HashMap<>();
    public final static HashMap<InfuseEffect, Integer> masterCooldownList = new HashMap<>();
    public static File cooldownFile;

    public static void initializeTimes(){
        cooldownFile = new File(InfuseRevampS2.instance.getDataFolder(), "cooldowns.yml");

        if (!cooldownFile.exists()) {
            try {
                cooldownFile.createNewFile();
                firstTimeSetup();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadCooldowns();
    }

    public static void saveCooldowns() {
        YamlConfiguration config = new YamlConfiguration();

        for (InfuseEffect effect : InfuseEffect.getEffectList()){
            Bukkit.getLogger().info("Saving effect " + effect.getName());
            String duration = String.valueOf(effect.getSparkDuration());
            String cooldown = String.valueOf(effect.getSparkCooldown());

            config.set(effect.getName() + ".duration", duration);
            config.set(effect.getName() + ".cooldown", cooldown);
        }

        config.set("swap", PlayerDataManager.globalSwapCooldown);

        try {
            config.save(cooldownFile);
            Bukkit.getLogger().info("Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCooldowns(){
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(cooldownFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        masterDurationList.clear();
        masterCooldownList.clear();

        for (String effectName : config.getKeys(false)) {
            if(effectName.equals("swap")) break;

            int duration = Integer.parseInt(String.valueOf(config.get(effectName + ".duration")));
            int cooldown = Integer.parseInt(String.valueOf(config.get(effectName + ".cooldown")));

            InfuseEffect effect = PlayerFileManager.parseEffect(effectName);

            masterDurationList.put(effect, duration);
            masterCooldownList.put(effect, cooldown);
        }

        PlayerDataManager.globalSwapCooldown = Long.parseLong(String.valueOf(config.get("swap")));
    }

    public static int getEffectDuration(InfuseEffect effect){
        return masterDurationList.getOrDefault(effect, 1);
    }
    public static int getEffectCooldown(InfuseEffect effect){
        return masterCooldownList.getOrDefault(effect, 1);
    }

    public static void firstTimeSetup(){
        YamlConfiguration config = new YamlConfiguration();
        config.set("none" + ".duration", 0);
        config.set("none" + ".cooldown", 0);

        config.set("strength" + ".duration", 20);
        config.set("strength" + ".cooldown", 9);

        config.set("heart" + ".duration", 90);
        config.set("heart" + ".cooldown", 15);

        config.set("haste" + ".duration", 30);
        config.set("haste" + ".cooldown", 6);

        config.set("invis" + ".duration", 15);
        config.set("invis" + ".cooldown", 6);

        config.set("feather" + ".duration", 7);
        config.set("feather" + ".cooldown", 3);

        config.set("frost" + ".duration", 20);
        config.set("frost" + ".cooldown", 7);

        config.set("thunder" + ".duration", 3);
        config.set("thunder" + ".cooldown", 12);

        config.set("regen" + ".duration", 15);
        config.set("regen" + ".cooldown", 7);

        config.set("ocean" + ".duration", 10);
        config.set("ocean" + ".cooldown", 3);

        config.set("fire" + ".duration", 10);
        config.set("fire" + ".cooldown", 4);

        config.set("emerald" + ".duration", 20);
        config.set("emerald" + ".cooldown", 6);

        config.set("speed" + ".duration", 1);
        config.set("speed" + ".cooldown", 1);

        config.set("ender" + ".duration", 30);
        config.set("ender" + ".cooldown", 9);

        config.set("enderfake" + ".duration", 20);
        config.set("enderfake" + ".cooldown", 9);

        config.set("apophis" + ".duration", 20);
        config.set("apophis" + ".cooldown", 12);

        config.set("swap", 6000);

        try {
            config.save(cooldownFile);
            Bukkit.getLogger().info("Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}