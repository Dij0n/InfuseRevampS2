package dijon.infuseRevampS2.data;

import dijon.infuseRevampS2.effects.InfuseEffect;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    public static int globalSwapCooldown; //This will be set by some cooldown loader

    public final static HashMap<UUID, PlayerData> masterPlayerDataList = new HashMap<>();

    public static void initialize(){

    }

    public static void addPlayer(PlayerData player){
        Bukkit.getLogger().info("Loaded a player: " + player.getUuid());
        masterPlayerDataList.put(player.getUuid(), player);
    }

    //GETTERS
    public static InfuseEffect getPrimary(UUID uuid) {
        return masterPlayerDataList.get(uuid).getPrimary();
    }
    public static InfuseEffect getSecondary(UUID uuid) {
        return masterPlayerDataList.get(uuid).getSecondary();
    }
    public static boolean isPrimaryActivated(UUID uuid) {
        return masterPlayerDataList.get(uuid).isPrimaryActivated();
    }
    public static boolean isSecondaryActivated(UUID uuid) {
        return masterPlayerDataList.get(uuid).isSecondaryActivated();
    }

    //As needed, pull methods from playerdata here. For now these are kinda the only ones you need

    //HELPERS

    public static void resetMasterPlayerList(){
        masterPlayerDataList.clear();
    }
    public static HashMap<UUID, PlayerData> getMasterPlayerDataList() {
        return masterPlayerDataList;
    }
    public static boolean isPlayerLoaded(UUID uuid){
        return masterPlayerDataList.containsKey(uuid);
    }
}
