package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class FrostListener implements Listener {

    public static final HashMap<UUID, Integer> frostedCooldownMap = new HashMap<>();
    public static final HashMap<UUID, Double> frostedJumpValues = new HashMap<>();


    public FrostListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!hasEffect(e.getDamager().getUniqueId())) return;

        if(e.getEntity() instanceof Player player){
            if(!ListenerHelpers.tenthHit(e)) return;
            player.setFreezeTicks(200);
            return;
        }
        if(e.getEntity() instanceof LivingEntity victim){
            if(e.getEntity().getType().equals(EntityType.ENDER_DRAGON) || e.getEntity().getType().equals(EntityType.WITHER) || e.getEntity().getType().equals(EntityType.ELDER_GUARDIAN)) return;
            victim.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 200, 99));
            return;
        }
    }

    @EventHandler
    public void onWindChargeGlobal(ProjectileLaunchEvent e){
        Bukkit.getLogger().info("Launched");
        Bukkit.getLogger().info(String.valueOf(e.getEntity().getShooter() instanceof Player));
        if(e.getEntity() instanceof WindCharge && e.getEntity().getShooter() instanceof Player player){
            Bukkit.getLogger().info("player!");
            Bukkit.getLogger().info(String.valueOf(player.getFreezeTicks()));
            if(player.getFreezeTicks() > 0){
                Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                    player.setCooldown(Material.WIND_CHARGE, 60);
                }, 1);
            }
        }
    }

    @EventHandler
    public void onAttackSparked(EntityDamageByEntityEvent e){
        if(!sparked(e.getDamager().getUniqueId())) return;
        if(!(e.getEntity() instanceof Player player)) return;

        player.setFreezeTicks(200);
        UUID uuid = player.getUniqueId();
        frostedJumpValues.putIfAbsent(uuid , 0.45);
        frostedJumpValues.put(uuid, frostedJumpValues.get(uuid) - 0.1d);
        if(frostedJumpValues.get(uuid) < 0) frostedJumpValues.put(uuid, 0d);

        frostedCooldownMap.putIfAbsent(uuid, 20); //Initialize Cooldown if not already initialized
        frostedCooldownMap.put(uuid, frostedCooldownMap.get(uuid) + 20); //Increase cooldown by 20 ticks
        BukkitRunnable task = new BukkitRunnable() {
            int counter = 20;
            @Override
            public void run() {
                frostedCooldownMap.put(uuid, frostedCooldownMap.get(uuid) - 1);
                counter--;
                if (counter <= 0) {
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(InfuseRevampS2.instance, 0, 1);
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.FROST);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.FROST);
    }

}