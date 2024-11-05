package dijon.infuseRevampS2.data;

import dijon.infuseRevampS2.effects.InfuseEffect;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerData {

    UUID uuid;
    InfuseEffect primary;
    InfuseEffect secondary;
    boolean primaryActivated = false;
    boolean secondaryActivated = false;
    long lastPrimaryActivation = 0;
    long lastSecondaryActivation = 0;
    long lastPrimarySwap = 0;
    long lastSecondarySwap = 0;

    ArrayList<UUID> trusted = new ArrayList<UUID>();

    public PlayerData(UUID uuid, InfuseEffect primary, InfuseEffect secondary){
        this.uuid = uuid;
        this.primary = primary;
        this.secondary = secondary;
    }

    //GETTERS
    public UUID getUuid() {
        return uuid;
    }
    public InfuseEffect getPrimary() {
        return primary;
    }
    public InfuseEffect getSecondary() {
        return secondary;
    }
    public boolean isPrimaryActivated() {
        return primaryActivated;
    }
    public boolean isSecondaryActivated() {
        return secondaryActivated;
    }
    public long getLastPrimaryActivation() {
        return lastPrimaryActivation;
    }
    public long getLastSecondaryActivation() {
        return lastSecondaryActivation;
    }
    public long getLastPrimarySwap() {
        return lastPrimarySwap;
    }
    public long getLastSecondarySwap() {
        return lastSecondarySwap;
    }

    //SETTERS
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
    public void setPrimary(InfuseEffect primary) {
        this.primary = primary;
    }
    public void setSecondary(InfuseEffect secondary) {
        this.secondary = secondary;
    }
    public void setPrimaryActivated(boolean primaryActivated) {
        this.primaryActivated = primaryActivated;
    }
    public void setSecondaryActivated(boolean secondaryActivated) {
        this.secondaryActivated = secondaryActivated;
    }
    public void setLastPrimaryActivation(long lastPrimaryActivation) {
        this.lastPrimaryActivation = lastPrimaryActivation;
    }
    public void setLastSecondaryActivation(long lastSecondaryActivation) {
        this.lastSecondaryActivation = lastSecondaryActivation;
    }
    public void setLastPrimarySwap(long lastPrimarySwap) {
        this.lastPrimarySwap = lastPrimarySwap;
    }
    public void setLastSecondarySwap(long lastSecondarySwap) {
        this.lastSecondarySwap = lastSecondarySwap;
    }

    //TRUSTED PLAYERS
    public ArrayList<UUID> getTrustedPlayerList() {
        return trusted;
    }
    public void setTrustedPlayerList(ArrayList<UUID> trusted) {
        this.trusted = trusted;
    }
    public void addTrustedPlayer(Player p){
        trusted.add(p.getUniqueId());
    }
    public void removeTrustedPlayer(Player p){
        trusted.remove(p.getUniqueId());
    }


    //COOLDOWN AND DURATION CALCULATORS
    public boolean isPrimaryDurationOver(){
        long timeDiff = System.currentTimeMillis() - getLastPrimaryActivation();
        return timeDiff > getPrimary().getSparkDuration() * 1000L;
    }
    public long getPrimaryDurationLeft(){
        long timeDiff = System.currentTimeMillis() - getLastPrimaryActivation();
        return (getPrimary().getSparkDuration() * 1000L) - timeDiff;
    }
    public boolean isPrimaryCooldownOver(){
        long timeDiff = System.currentTimeMillis() - getLastPrimaryActivation();
        return timeDiff > (getPrimary().getSparkDuration() + getPrimary().getSparkCooldown()) * 1000L;
    }
    public long getPrimaryCooldownLeft(){
        long timeDiff = System.currentTimeMillis() - getLastPrimaryActivation();
        return ((getPrimary().getSparkDuration() + getPrimary().getSparkCooldown()) * 1000L) - timeDiff;
    }

    public boolean isSecondaryDurationOver(){
        long timeDiff = System.currentTimeMillis() - getLastSecondaryActivation();
        return timeDiff > getSecondary().getSparkDuration() * 1000L;
    }
    public long getSecondaryDurationLeft(){
        long timeDiff = System.currentTimeMillis() - getLastSecondaryActivation();
        return (getSecondary().getSparkDuration() * 1000L) - timeDiff;
    }
    public boolean isSecondaryCooldownOver(){
        long timeDiff = System.currentTimeMillis() - getLastSecondaryActivation();
        return timeDiff > (getSecondary().getSparkDuration() + getSecondary().getSparkCooldown()) * 1000L;
    }
    public long getSecondaryCooldownLeft(){
        long timeDiff = System.currentTimeMillis() - getLastSecondaryActivation();
        return ((getSecondary().getSparkDuration() + getSecondary().getSparkCooldown()) * 1000L) - timeDiff;
    }

    public boolean isPrimarySwapCooldownOver(){
        long timeDiff = System.currentTimeMillis() - getLastPrimarySwap();
        return timeDiff > PlayerDataManager.globalSwapCooldown;
    }
    public long getPrimarySwapCooldownLeft(){
        long timeDiff = System.currentTimeMillis() - getLastPrimarySwap();
        return PlayerDataManager.globalSwapCooldown - timeDiff;
    }
    public boolean isSecondarySwapCooldownOver(){
        long timeDiff = System.currentTimeMillis() - getLastSecondarySwap();
        return timeDiff > PlayerDataManager.globalSwapCooldown;
    }
    public long getSecondarySwapCooldownLeft(){
        long timeDiff = System.currentTimeMillis() - getLastSecondarySwap();
        return PlayerDataManager.globalSwapCooldown - timeDiff;
    }

    public void setEverything(PlayerData data){
        this.uuid = data.getUuid();
        this.primary = data.getPrimary();
        this.secondary = data.getSecondary();
        this.primaryActivated = data.isPrimaryActivated();
        this.secondaryActivated = data.isSecondaryActivated();
        this.lastPrimaryActivation = data.getLastPrimaryActivation();
        this.lastSecondaryActivation = data.getLastSecondaryActivation();
        this.lastPrimarySwap = data.getLastPrimarySwap();
        this.lastSecondarySwap = data.getLastSecondarySwap();
        this.trusted = data.getTrustedPlayerList();
    }

}
