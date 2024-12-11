package dijon.infuseRevampS2.EffectActions.Spawnables.Runnables;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.FireListener;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.scheduler.BukkitRunnable;

public class CustomFire extends BukkitRunnable {

    LivingEntity livingEntity;

    int timer = 0;
    double damage = 0.5;
    int maxTime = 140;

    public CustomFire(LivingEntity livingEntity){
        this.livingEntity = livingEntity;
        runTaskTimer(InfuseRevampS2.instance, 0,1);
    }

    @Override
    public void run() {

        if(PlayerDataManager.hasEffect(livingEntity.getUniqueId(), InfuseEffect.FIRE)) fullCancel();
        if(livingEntity instanceof Villager) fullCancel();

        if(timer % 18 == 0){
            livingEntity.setFireTicks(17); //Allows for constant fire image without constant fire damage
        }

        if(livingEntity.getFireTicks() <= 0){
            if((timer + 1) % 18 != 0){
                fullCancel();
            }
        }

        if(timer % 20 == 0){
            Helpers.trueDamage(livingEntity, damage);
            livingEntity.getWorld().playSound(livingEntity, Sound.BLOCK_FIRE_AMBIENT, 0.2f, 1);
            damage += 0.5;
            if(damage > 4){
                damage = 4;
            }
        }

        if(timer > 140) fullCancel();
        if(livingEntity.isDead()) fullCancel();
        if(!livingEntity.getWorld().isClearWeather()) fullCancel();

        timer++;
    }

    public void reset(){
        maxTime += timer;
    }

    public void fullCancel(){
        FireListener.customFireHolder.remove(livingEntity.getUniqueId());
        cancel();
    }

}
