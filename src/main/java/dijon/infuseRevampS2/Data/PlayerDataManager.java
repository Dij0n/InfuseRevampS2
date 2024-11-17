package dijon.infuseRevampS2.Data;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    public static long globalSwapCooldown; //This is set in CooldownFileManager

    public final static HashMap<UUID, PlayerData> masterPlayerDataList = new HashMap<>();

    public static void initialize(){

    }

    public static void addPlayer(PlayerData player){
        Bukkit.getLogger().info("Initialized player: " + player.getUuid());
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

    //SETTERS
    public static void setPrimary(UUID uuid, InfuseEffect infuseEffect){
        masterPlayerDataList.get(uuid).setPrimary(infuseEffect);
    }
    public static void setSecondary(UUID uuid, InfuseEffect infuseEffect){
        masterPlayerDataList.get(uuid).setSecondary(infuseEffect);
    }
    public static void setLastPrimaryActivation(UUID uuid, long time){
        masterPlayerDataList.get(uuid).setLastPrimaryActivation(time);
    }
    public static void setLastSecondaryActivation(UUID uuid, long time){
        masterPlayerDataList.get(uuid).setLastSecondaryActivation(time);
    }
    public static void setLastPrimarySwap(UUID uuid, long time){
        masterPlayerDataList.get(uuid).setLastPrimarySwap(time);
    }
    public static void setLastSecondarySwap(UUID uuid, long time){
        masterPlayerDataList.get(uuid).setLastSecondarySwap(time);
    }

    //COOLDOWNS
    public static boolean isPrimaryCooldownOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isPrimaryCooldownOver();
    }
    public static boolean isSecondaryCooldownOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isSecondaryCooldownOver();
    }
    public static long getPrimaryCooldownLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getPrimaryCooldownLeft();
    }
    public static long getSecondaryCooldownLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getSecondaryCooldownLeft();
    }
    public static boolean isPrimarySwapCooldownOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isPrimarySwapCooldownOver();
    }
    public static boolean isSecondarySwapCooldownOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isSecondarySwapCooldownOver();
    }
    public static long getPrimarySwapCooldownLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getPrimarySwapCooldownLeft();
    }
    public static long getSecondarySwapCooldownLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getSecondarySwapCooldownLeft();
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
