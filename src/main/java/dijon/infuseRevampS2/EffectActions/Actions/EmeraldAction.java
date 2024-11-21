package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.ListenerHelpers;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class EmeraldAction extends InfuseAction {

    public EmeraldAction(){
        super(InfuseEffect.EMERALD);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {
        ListenerHelpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), ListenerHelpers.swords, Enchantment.LOOTING, 5);
        ListenerHelpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), ListenerHelpers.swords, Enchantment.LOOTING, 5);
    }

    @Override
    protected void onSparked(Player player) {

    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 30, 99));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 30, 2));
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 30, 99));
                player.addPotionEffect(new PotionEffect(PotionEffectType.HERO_OF_THE_VILLAGE, 30, 99));
            }
        };
    }
}
