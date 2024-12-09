package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ThunderListener implements Listener {

    public ThunderListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        if(e.getDamager().getWorld().isClearWeather()){
            if(!Helpers.tenthHit(e)) return;
        }else{
            if(!Helpers.fifthHit(e)) return;
        }

        LivingEntity victim = (LivingEntity) e.getEntity();
        Player attacker = (Player) e.getDamager();

        chainSpark(attacker, attacker, victim, 0);
    }


    @EventHandler
    public void onTridentLand(ProjectileHitEvent e){
        if(!(e.getEntity() instanceof Trident trident)) return;
        if(!(e.getHitEntity() instanceof LivingEntity livingEntity)) return;
        if(!(e.getEntity().getShooter() instanceof Player player)) return;
        if(!hasEffect(player.getUniqueId())) return;

        if(livingEntity instanceof Player opp){
            if(opp.isBlocking()){
                return;
            }
        }

        livingEntity.getWorld().strikeLightningEffect(e.getHitEntity().getLocation());
        Helpers.trueDamage(livingEntity, 2);

    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.THUNDER);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.THUNDER);
    }


    public void chainSpark(Player caster, LivingEntity source, LivingEntity target, int total){

        if(total >= 10) return;

        target.getWorld().strikeLightningEffect(target.getLocation());
        target.getWorld().playSound(target, Sound.ITEM_TRIDENT_THUNDER, 2, 1);

        Helpers.trueDamage(target, 4);

        ArrayList<UUID> casterTrusted = PlayerDataManager.getTrustedList(caster.getUniqueId());

        LivingEntity nextBitch = null;
        for(Entity e : target.getNearbyEntities(3, 3, 3)){
            if(!(e instanceof LivingEntity le)) continue;
            if(e instanceof Villager) continue;

            if(le.equals(source) || le.equals(caster) || casterTrusted.contains(le.getUniqueId())) continue;

            if(nextBitch == null){
                nextBitch = le;
                continue;
            }

            if(target.getLocation().distance(le.getLocation()) < target.getLocation().distance(nextBitch.getLocation())){
                nextBitch = le;
            }
        }


        if(nextBitch != null){
            LivingEntity finalNextBitch = nextBitch;
            int finalTotal = total + 1;
            pointsWithMurderousIntent(finalNextBitch).runTaskTimer(InfuseRevampS2.instance, 0, 2);
            Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
                chainSpark(caster, target, finalNextBitch, finalTotal);
            }, 20);
        }
    }

    public BukkitRunnable pointsWithMurderousIntent(LivingEntity player){
        return new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.TRIAL_SPAWNER_DETECTION_OMINOUS, player.getLocation().add(0, 1, 0), 10, 0.25, 0.25, 0.25, 0.01);
                count++;
                if(count > 10) cancel();
            }
        };
    }

}
