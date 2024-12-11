package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.FireListener;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Spawnables.Particles.FireParticles;
import dijon.infuseRevampS2.EffectActions.Spawnables.Runnables.CustomFire;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import net.bytebuddy.implementation.InvokeDynamic;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.List;

public class FireAction extends InfuseAction {

    public FireAction(){
        super(InfuseEffect.FIRE);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {
        player.setAllowFlight(false);
        player.setFlySpeed(0.1f);
        player.setFlying(false);
    }

    @Override
    protected void onSparked(Player player) {
        new FireParticles(player, InfuseEffect.FIRE.getSparkDuration(), 5);
    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                genericRunnable(player);
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {

            int count = 0; //Special exception for fire because stupid
            final long maxCount = InfuseEffect.FIRE.getSparkDuration() * 10L;

            @Override
            public void run() {
                genericRunnable(player);
                clearAllWater(player);
                player.setFireTicks(10);
                for(Entity e : player.getNearbyEntities(7, 7,7)){
                    if(!(e instanceof LivingEntity livingEntity)) continue;

                    if(PlayerDataManager.hasEffect(livingEntity.getUniqueId(), InfuseEffect.FIRE)){
                        if(livingEntity.getLocation().distance(player.getLocation()) > 5){
                            if(FireListener.customFireHolder.containsKey(livingEntity.getUniqueId())){
                                FireListener.customFireHolder.get(livingEntity.getUniqueId()).fullCancel();
                            }
                        }else{
                            FireListener.applyOrRefreshCustomFire(livingEntity);
                        }
                    }else{
                        if(livingEntity.getLocation().distance(player.getLocation()) <= 5){
                            FireListener.applyOrRefreshCustomFire(livingEntity);
                        }
                    }
                }

                count++;
                if(count >= maxCount){
                    volcano(player);
                    cancel();
                }
            }
        };
    }


    public void genericRunnable(Player player){
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 30, 0));

        if(player.getGameMode().equals(GameMode.CREATIVE) || player.getGameMode().equals(GameMode.SPECTATOR)){
            player.setAllowFlight(true);
            player.setFlySpeed(0.1f);
        }else{
            boolean inLava = player.getLocation().add(new Vector(0, 0.25, 0)).getBlock().getType() == Material.LAVA;
            if(inLava){
                player.setAllowFlight(true);
                player.setFlySpeed(0.05f);
                player.setFlying(true);
            }else{
                player.setAllowFlight(false);
                player.setFlySpeed(0.1f);
                player.setFlying(false);
            }
        }
    }

    public void clearAllWater(Player player){
        for(int x = player.getLocation().getBlockX() - 5; x < player.getLocation().getBlockX() + 6; x++){
            for(int y = player.getLocation().getBlockY() - 5; y < player.getLocation().getBlockY() + 6; y++){
                for(int z = player.getLocation().getBlockZ() - 5; z < player.getLocation().getBlockZ() + 6; z++){
                    Block block = player.getWorld().getBlockAt(new Location(player.getWorld(), x, y, z));
                    if(block.getType().equals(Material.WATER) || block.getType().equals(Material.POWDER_SNOW)){
                        block.setType(Material.AIR);
                        player.getWorld().spawnParticle(Particle.SMOKE, block.getLocation(), 30, 0.2, 0.2, 0.2, 0.01);
                        player.getWorld().playSound(block.getLocation(), Sound.BLOCK_LAVA_EXTINGUISH, 0.02f, 1);
                    }
                }
            }
        }
    }

    public void volcano(Player player){
        for(Entity e : player.getNearbyEntities(5, 5,5)){
            if(!(e instanceof LivingEntity livingEntity)) continue;
            livingEntity.getWorld().spawnParticle(Particle.DUST_PILLAR, livingEntity.getLocation().add(new Vector(0, 0, 0)), 50, 0.1, 0.1, 0.1, Material.LAVA.createBlockData());
            livingEntity.getWorld().playSound(livingEntity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
            livingEntity.setVelocity(new Vector(0, 3, 0));
            Helpers.trueDamage(livingEntity, 3);
        }
    }
}


