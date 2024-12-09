package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.FrostListener;
import dijon.infuseRevampS2.EffectActions.Listeners.SpeedListener;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FrostAction extends InfuseAction {

    public FrostAction(){
        super(InfuseEffect.FROST);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {

    }

    @Override
    protected void onSparked(Player player) {

    }

    @Override
    protected void onSparkEnd(Player player) {
        FrostListener.frostedCooldownMap.clear();
        FrostListener.frostedJumpValues.clear();
        for(Player enemy : Bukkit.getOnlinePlayers()){
            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
        }
    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                checkBlock(player);
                for(Player enemy : Bukkit.getOnlinePlayers()){
                    if(!FrostListener.frostedCooldownMap.containsKey(enemy.getUniqueId())){
                        if(enemy.getFreezeTicks() > 0){
                            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.35);
                        }else{
                            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
                        }
                    }
                }
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                checkBlock(player);

                for(Player enemy : Bukkit.getOnlinePlayers()){
                    if(FrostListener.frostedCooldownMap.containsKey(enemy.getUniqueId())){
                        enemy.setFreezeTicks(340);
                        if(FrostListener.frostedCooldownMap.getOrDefault(enemy.getUniqueId(), 0) > 0){
                            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(FrostListener.frostedJumpValues.getOrDefault(enemy.getUniqueId(), 0.35));
                        }else{
                            FrostListener.frostedJumpValues.remove(enemy.getUniqueId());
                            FrostListener.frostedCooldownMap.remove(enemy.getUniqueId());
                        }
                    }else{
                        if(enemy.getFreezeTicks() > 0){
                            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.35);
                        }else{
                            enemy.getAttribute(Attribute.GENERIC_JUMP_STRENGTH).setBaseValue(0.42);
                        }
                    }
                }

            }
        };
    }


    private void checkBlock(Player player){
        Block blockUnder = player.getWorld().getBlockAt(player.getLocation().add(new Vector(0, -1, 0)));
        if(blockUnder.getType().equals(Material.SNOW_BLOCK) || blockUnder.getType().equals(Material.ICE) || blockUnder.getType().equals(Material.PACKED_ICE) || blockUnder.getType().equals(Material.BLUE_ICE)){
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20, 9));
        }else if(!blockUnder.getType().equals(Material.AIR)){
            player.removePotionEffect(PotionEffectType.SPEED);
        }



        if(blockUnder.getType().equals(Material.POWDER_SNOW)){

        }
    }
}
