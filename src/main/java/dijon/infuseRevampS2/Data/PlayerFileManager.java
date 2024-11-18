package dijon.infuseRevampS2.Data;

import dijon.infuseRevampS2.InfuseRevampS2;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerFileManager {

    public static File playerDataFile;

    public static void initialize(){

        playerDataFile = new File(InfuseRevampS2.instance.getDataFolder(), "playerdata.yml");

        if (!playerDataFile.exists()) {
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        loadData();
    }

    public static void saveData() {
        YamlConfiguration config = new YamlConfiguration();

        HashMap<UUID, PlayerData> playerData = PlayerDataManager.getMasterPlayerDataList();

        for (PlayerData save : playerData.values()){
            Bukkit.getLogger().info("Saving player " + save.getUuid());
            String uuid = save.getUuid().toString();
            String primary = save.getPrimary().getName();
            String secondary = save.getSecondary().getName();
            String lastPrimary = String.valueOf(save.getLastPrimaryActivation());
            String lastSecondary = String.valueOf(save.getLastSecondaryActivation());
            String lastPrimarySwap = String.valueOf(save.getLastPrimarySwap());
            String lastSecondarySwap = String.valueOf(save.getLastSecondarySwap());
            ArrayList<UUID> trustedList = save.getTrustedPlayerList();

            ArrayList<String> trustedListStrings = new ArrayList<>();
            for(UUID trustedUUID : trustedList){
                trustedListStrings.add(trustedUUID.toString());
            }


            config.set(uuid + ".primary", primary);
            config.set(uuid + ".secondary", secondary);
            config.set(uuid + ".lastPrimaryActivation", lastPrimary);
            config.set(uuid + ".lastSecondaryActivation", lastSecondary);
            config.set(uuid + ".lastPrimarySwap", lastPrimarySwap);
            config.set(uuid + ".lastSecondarySwap", lastSecondarySwap);
            config.set(uuid + ".trustedPlayers", trustedListStrings);
        }

        try {
            config.save(playerDataFile);
            Bukkit.getLogger().info("Saved");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadData(){
        YamlConfiguration config = new YamlConfiguration();

        try {
            config.load(playerDataFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        PlayerDataManager.resetMasterPlayerList();

        for (String uuid : config.getKeys(false)) {
            Bukkit.getLogger().info("Loading player " + uuid);

            InfuseEffect primary = parseEffect((String) config.get(uuid + ".primary"));
            InfuseEffect secondary = parseEffect((String) config.get(uuid + ".secondary"));
            long lastPrimary = Long.parseLong((String) config.get(uuid + ".lastPrimaryActivation"));
            long lastSecondary = Long.parseLong((String) config.get(uuid + ".lastSecondaryActivation"));
            long lastPrimarySwap = Long.parseLong((String) config.get(uuid + ".lastPrimarySwap"));
            long lastSecondarySwap = Long.parseLong((String) config.get(uuid + ".lastSecondarySwap"));
            List<String> trustedList = config.getStringList(uuid + ".trustedPlayers");

            ArrayList<UUID> trustedListUUIDs = new ArrayList<>();
            for(String listedUUID : trustedList){
                trustedListUUIDs.add(UUID.fromString(listedUUID));
            }

            PlayerData save = new PlayerData(UUID.fromString(uuid), primary, secondary);

            save.setPrimaryActivated(false);
            save.setSecondaryActivated(false);
            save.setLastPrimaryActivation(lastPrimary);
            save.setLastSecondaryActivation(lastSecondary);
            save.setLastPrimarySwap(lastPrimarySwap);
            save.setLastSecondarySwap(lastSecondarySwap);
            save.setTrustedPlayerList(trustedListUUIDs);

            PlayerDataManager.addPlayer(save);
        }

    }

    public static InfuseEffect parseEffect(String name){

        for(InfuseEffect effect : InfuseEffect.getEffectList()){
            if (effect.getName().equalsIgnoreCase(name)){
                return effect;
            }
        }
        return InfuseEffect.NONE;
    }


}
