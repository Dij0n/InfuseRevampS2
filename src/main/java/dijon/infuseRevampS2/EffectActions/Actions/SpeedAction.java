package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Listeners.SpeedListener;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.Collections;

public class SpeedAction extends InfuseAction {

    public SpeedAction(){
        super(InfuseEffect.SPEED);
    }

    @Override
    protected void onEquip(Player player) {
        SpeedListener.speedCooldownMap.put(player.getUniqueId(), 0);
        SpeedListener.speedLevelMap.put(player.getUniqueId(), 0);
    }

    @Override
    protected void onUnequipped(Player player) {
        if(!(player.getInventory().getItemInMainHand().getType().equals(Material.CROSSBOW) && player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3)){
            Helpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
        }
        if(!(player.getInventory().getItemInOffHand().getType().equals(Material.CROSSBOW) && player.getInventory().getItemInOffHand().getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3)){
            Helpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
        }
    }

    @Override
    protected void onSparked(Player player) {
        Vector v = player.getLocation().getDirection().normalize().multiply(2.25);
        v.setY(v.getY() / 2);
        player.setVelocity(v);
        BukkitRunnable runnable = new BukkitRunnable() {
            int count = 0;
            @Override
            public void run() {
                player.getWorld().spawnParticle(Particle.FLAME, player.getLocation(), 3);
                count++;
                if(count>20) cancel();
            }
        };
        runnable.runTaskTimer(InfuseRevampS2.instance, 0, 1);
    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if(SpeedListener.speedCooldownMap.getOrDefault(player.getUniqueId(), 0) > 0){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, SpeedListener.speedLevelMap.get(player.getUniqueId())));
                }else{
                    SpeedListener.speedLevelMap.put(player.getUniqueId(), 0);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
                }
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if(SpeedListener.speedCooldownMap.getOrDefault(player.getUniqueId(), 0) >= 0){
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, SpeedListener.speedLevelMap.get(player.getUniqueId())));
                }else{
                    SpeedListener.speedLevelMap.put(player.getUniqueId(), 0);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 0));
                }

            }
        };
    }
}
