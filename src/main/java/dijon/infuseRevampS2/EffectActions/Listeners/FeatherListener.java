package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class FeatherListener implements Listener {

    public static final HashMap<UUID, Boolean> featherPoundMap = new HashMap<>();

    public FeatherListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }


    @EventHandler
    public void featherStop(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if(!hasEffect(player.getUniqueId())) return;
        if(!(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) || e.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL))) return;

        e.setCancelled(true);
    }

    @EventHandler
    public void featherMace(EntityDamageByEntityEvent e){
        if(!Helpers.playerAndMob(e)) return;
        if(hasEffect(e.getDamager().getUniqueId())){
            if(e.getDamager().getFallDistance() > 20){
                e.setDamage(e.getDamage() * 1.5);
            }
        }
    }

    @EventHandler
    public void featherMobHitPlayer(EntityDamageByEntityEvent e){
        if(!Helpers.mobAndPlayer(e)) return;
        if(e.getDamager() instanceof Player) return;
        if(hasEffect(e.getEntity().getUniqueId())){
            spawnWindCharge((LivingEntity) e.getDamager(), (LivingEntity) e.getEntity());
        }
    }

    @EventHandler
    public void featherPlayerHitPlayer(EntityDamageByEntityEvent e){
        if(!Helpers.bothPlayers(e)) return;
        if(!Helpers.tenthHit(e)) return;
        if(hasEffect(e.getEntity().getUniqueId())){
            spawnWindCharge((LivingEntity) e.getDamager(), (LivingEntity) e.getEntity());
        }
    }

    @EventHandler
    public void featherWindLaunch(ProjectileLaunchEvent e){
        if(!(e.getEntity().getShooter() instanceof Player player)) return;
        if(!(e.getEntity() instanceof WindCharge windCharge)) return;
        if(hasEffect(player.getUniqueId())){
            windCharge.setAcceleration(windCharge.getAcceleration().multiply(2));
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                player.setCooldown(Material.WIND_CHARGE, 4);
            }, 1);
        }

    }



    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.FEATHER);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.FEATHER);
    }


    private void spawnWindCharge(LivingEntity attacker, LivingEntity featherUser){

        Location chargeLoc = attacker.getLocation().add(new Vector(0,0.1,0));
        Vector v = attacker.getEyeLocation().getDirection().normalize();
        v.setY(0);
        chargeLoc.add(v.multiply(0.2));

        attacker.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0));

        WindCharge windCharge = (WindCharge) featherUser.getWorld().spawnEntity(chargeLoc, EntityType.WIND_CHARGE);
        windCharge.setAcceleration(windCharge.getAcceleration().multiply(2));
        Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
            featherUser.setVelocity(new Vector(0,0,0));
        }, 1);
    }

}
