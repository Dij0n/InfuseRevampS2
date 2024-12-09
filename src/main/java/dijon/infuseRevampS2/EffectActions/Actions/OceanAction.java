package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Spawnables.Objects.Whirlpool;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class OceanAction extends InfuseAction {

    public OceanAction(){
        super(InfuseEffect.OCEAN);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {

    }

    @Override
    protected void onSparked(Player player) {
        new Whirlpool(player.getLocation(), player);
    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return genericRunnable(player);
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return genericRunnable(player);
    }


    public BukkitRunnable genericRunnable(Player player){
        return new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {

                count++;

                player.addPotionEffect(new PotionEffect(PotionEffectType.DOLPHINS_GRACE, 30, 0));
                player.addPotionEffect(new PotionEffect(PotionEffectType.CONDUIT_POWER, 30, 0));

                for(Entity e : player.getNearbyEntities(5, 5, 5)){

                    if(e instanceof Villager) continue;
                    if(e instanceof Player opp){

                        if(PlayerDataManager.getTrustedList(player.getUniqueId()).contains(opp.getUniqueId())) continue;
                        if(PlayerDataManager.hasEffectSparked(opp.getUniqueId(), InfuseEffect.OCEAN)) continue;

                        int drainTime = 11;
                        if(!opp.getWorld().isClearWeather()) drainTime = 14;

                        opp.setRemainingAir(Math.max(opp.getRemainingAir() - drainTime, -10));
                        opp.getWorld().spawnParticle(Particle.BUBBLE_POP,opp.getLocation().add(new Vector(0, 1, 0)), 30, 0.2, 0.5, 0.2,0);

                        if(count % 10 == 0 && opp.getRemainingAir() < 0){
                            Helpers.trueDamage(opp, 2);
                        }

                    }else if(e instanceof LivingEntity livingEntity){
                        livingEntity.getWorld().spawnParticle(Particle.BUBBLE_POP,livingEntity.getLocation().add(new Vector(0, 1, 0)), 30, 0.2, 0.5, 0.2,0);
                        if(count % 10 == 0){
                            Helpers.trueDamage(livingEntity, 2);
                        }

                    }

                }

            }
        };
    }
}
