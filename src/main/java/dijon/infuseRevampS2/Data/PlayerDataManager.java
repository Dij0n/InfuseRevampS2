package dijon.infuseRevampS2.Data;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class PlayerDataManager {

    public static long globalSwapCooldown; //This is set in CooldownFileManager

    public final static HashMap<UUID, PlayerData> masterPlayerDataList = new HashMap<>();

    public static void initialize(){

    }

    public static void addPlayer(PlayerData player){
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
    public static boolean hasEffect(UUID uuid, InfuseEffect effect){
        if(Bukkit.getPlayer(uuid) == null) return false;
        return getPrimary(uuid).equals(effect) || getSecondary(uuid).equals(effect);
    }
    public static boolean hasEffectSparked(UUID uuid, InfuseEffect effect){
        if(!hasEffect(uuid, effect)) return false;

        if(getPrimary(uuid).equals(effect) && isPrimaryActivated(uuid)) return true;
        if(getSecondary(uuid).equals(effect) && isSecondaryActivated(uuid)) return true;
        
        return false;
    }
    public static ArrayList<UUID> getTrustedList(UUID uuid){
        return masterPlayerDataList.get(uuid).getTrustedPlayerList();
    }
    public static int getHitCount(UUID uuid){
        return masterPlayerDataList.get(uuid).getHitCount();
    }
    public static long getLastPrimaryActivation(UUID uuid) {
        return masterPlayerDataList.get(uuid).getLastPrimaryActivation();
    }
    public static long getLastSecondaryActivation(UUID uuid) {
        return masterPlayerDataList.get(uuid).getLastSecondaryActivation();
    }

    //SETTERS
    public static void setPrimary(UUID uuid, InfuseEffect infuseEffect){
        masterPlayerDataList.get(uuid).setPrimary(infuseEffect);
    }
    public static void setSecondary(UUID uuid, InfuseEffect infuseEffect){
        masterPlayerDataList.get(uuid).setSecondary(infuseEffect);
    }
    public static void setPrimaryActive(UUID uuid, boolean active){
        masterPlayerDataList.get(uuid).setPrimaryActivated(active);
    }
    public static void setSecondaryActive(UUID uuid, boolean active){
        masterPlayerDataList.get(uuid).setSecondaryActivated(active);
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

    public static void incHitCount(UUID uuid){
        masterPlayerDataList.get(uuid).incHitCount();
    }
    public static void resetHitCount(UUID uuid){
        masterPlayerDataList.get(uuid).resetHitCount();
    }

    //COOLDOWNS
    public static boolean isPrimarySparkDurationOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isPrimaryDurationOver();
    }
    public static boolean isSecondarySparkDurationOver(UUID uuid) {
        return masterPlayerDataList.get(uuid).isSecondaryDurationOver();
    }
    public static long getPrimarySparkDurationLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getPrimaryDurationLeft();
    }
    public static long getSecondarySparkDurationLeft(UUID uuid){
        return masterPlayerDataList.get(uuid).getSecondaryDurationLeft();
    }
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
    public static void addTrustedPlayer(UUID uuid, Player p){
        masterPlayerDataList.get(uuid).addTrustedPlayer(p);
    }
    public static void removeTrustedPlayer(UUID uuid, Player p){
        masterPlayerDataList.get(uuid).removeTrustedPlayer(p);
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
