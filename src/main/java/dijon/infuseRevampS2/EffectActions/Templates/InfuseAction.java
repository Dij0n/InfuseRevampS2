package dijon.infuseRevampS2.EffectActions.Templates;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public abstract class InfuseAction {

    public HashMap<UUID, BukkitRunnable> currentlyActiveInterims = new HashMap<>();

    public InfuseEffect effect;

    public InfuseAction(InfuseEffect effect){
        this.effect = effect;
    }

    public void runEquipTask(Player player){
        onEquip(player);
        addStandardRunnable(player.getUniqueId());
    }

    public void runUnequippedTask(Player player){
        onSparkEnd(player);
        onUnequipped(player);
        stopActiveRunnable(player.getUniqueId());
    }

    public void runSparkTask(Player player, boolean isOnLeft){
        stopActiveRunnable(player.getUniqueId());
        onSparked(player);
        addSparkedRunnable(player.getUniqueId());
        Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, () ->{
            runSparkEndTask(player);
            if(isOnLeft){
                PlayerDataManager.setPrimaryActive(player.getUniqueId(), false);
            }else{
                PlayerDataManager.setSecondaryActive(player.getUniqueId(), false);
            }
        }, this.effect.getSparkDuration() * 20L);
    }

    public void runSparkEndTask(Player player){
        stopActiveRunnable(player.getUniqueId());
        onSparkEnd(player);
        addStandardRunnable(player.getUniqueId());
    }

    public void runLeaveTask(Player player){
        onSparkEnd(player);
        stopActiveRunnable(player.getUniqueId());
    }

    public void runReJoinTask(Player player){
        onEquip(player);
        addStandardRunnable(player.getUniqueId());
    }

    protected abstract void onEquip(Player player);
    protected abstract void onUnequipped(Player player);
    protected abstract void onSparked(Player player);
    protected abstract void onSparkEnd(Player player);
    protected abstract BukkitRunnable createStandardInterim(Player player);
    protected abstract BukkitRunnable createSparkedInterim(Player player);



    protected void stopActiveRunnable(UUID uuid){
        if(currentlyActiveInterims.containsKey(uuid)){
            currentlyActiveInterims.get(uuid).cancel();
        }
        currentlyActiveInterims.remove(uuid);
    }
    protected void addStandardRunnable(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;
        BukkitRunnable task = createStandardInterim(player);
        if(task != null){
            task.runTaskTimer(InfuseRevampS2.instance, 0, 2);
            currentlyActiveInterims.put(uuid, task);
        }
    }
    protected void addSparkedRunnable(UUID uuid){
        Player player = Bukkit.getPlayer(uuid);
        if(player == null) return;
        BukkitRunnable task = createSparkedInterim(player);
        if(task != null){
            task.runTaskTimer(InfuseRevampS2.instance, 0, 2);
            currentlyActiveInterims.put(uuid, task);
        }
    }


}
