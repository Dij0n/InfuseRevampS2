package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.EffectActions.Particles.InvisParticles;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class InvisAction extends InfuseAction {

    public InvisAction(){
        super(InfuseEffect.INVIS);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {

    }

    @Override
    protected void onSparked(Player player) {
        new InvisParticles(player, PlayerDataManager.getTrustedList(player.getUniqueId()), InfuseEffect.INVIS.getSparkDuration() - 5, 2);
    }

    @Override
    protected void onSparkEnd(Player player) {
        ListenerHelpers.showPlayer(player);
        for(UUID uuid : PlayerDataManager.getTrustedList(player.getUniqueId())){
            if(Bukkit.getPlayer(uuid) != null){
                Player teammate = Bukkit.getPlayer(uuid);
                ListenerHelpers.showPlayer(teammate);
            }
        }
    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30, 0));
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 30, 0));
                ListenerHelpers.hidePlayer(player);
                player.playSound(player, Sound.ENTITY_BREEZE_IDLE_GROUND, 2, 1);
                for(Entity e : player.getNearbyEntities(10, 4, 10)){
                    e.getWorld().playSound(e, Sound.ENTITY_BREEZE_IDLE_GROUND, 2, 1);
                    if(!(e instanceof Player victim)) continue;
                    if(e.equals(player)) continue;
                    if(!PlayerDataManager.getTrustedList(player.getUniqueId()).contains(victim.getUniqueId())){
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20, 0));
                        victim.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20, 0));
                    }
                }

                for(UUID uuid : PlayerDataManager.getTrustedList(player.getUniqueId())){
                    if(Bukkit.getPlayer(uuid) != null){
                        Player teammate = Bukkit.getPlayer(uuid);
                        if(teammate.getNearbyEntities(10, 4, 10).contains(player)){
                            ListenerHelpers.hidePlayer(teammate);
                        }else{
                            ListenerHelpers.showPlayer(teammate);
                        }
                    }
                }

            }
        };
    }
}
