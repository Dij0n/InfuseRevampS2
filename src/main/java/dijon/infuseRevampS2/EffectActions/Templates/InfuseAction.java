package dijon.infuseRevampS2.EffectActions.Templates;

import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public abstract class InfuseAction {

    final public static HashMap<UUID, BukkitRunnable> currentlyActiveInterims = new HashMap<>();

    public void runEquipTask(Player player){
        onEquip(player);
        addStandardRunnable(player.getUniqueId());
    }

    public void runUnequippedTask(Player player){
        onSparkEnd(player);
        onUnequipped(player);
        stopActiveRunnable(player.getUniqueId());
    }

    public void runSparkTask(Player player){
        stopActiveRunnable(player.getUniqueId());
        onSparked(player);
        addSparkedRunnable(player.getUniqueId());
    }

    public void runSparkEndTask(Player player){
        stopActiveRunnable(player.getUniqueId());
        onSparkEnd(player);
        addStandardRunnable(player.getUniqueId());
    }

    protected abstract void onEquip(Player player);
    protected abstract void onUnequipped(Player player);
    protected abstract void onSparked(Player player);
    protected abstract void onSparkEnd(Player player);
    protected abstract BukkitRunnable createStandardInterim();
    protected abstract BukkitRunnable createSparkedInterim();



    protected void stopActiveRunnable(UUID uuid){
        if(currentlyActiveInterims.containsKey(uuid)){
            currentlyActiveInterims.get(uuid).cancel();
        }
        currentlyActiveInterims.remove(uuid);
    }
    protected void addStandardRunnable(UUID uuid){
        BukkitRunnable task = createStandardInterim();
        if(task != null){
            task.runTaskTimer(InfuseRevampS2.instance, 0, 2);
            currentlyActiveInterims.put(uuid, task);
        }
    }
    protected void addSparkedRunnable(UUID uuid){
        BukkitRunnable task = createSparkedInterim();
        if(task != null){
            task.runTaskTimer(InfuseRevampS2.instance, 0, 2);
            currentlyActiveInterims.put(uuid, task);
        }
    }


}
