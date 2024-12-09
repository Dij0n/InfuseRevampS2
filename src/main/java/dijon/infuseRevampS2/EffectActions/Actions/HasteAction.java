package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class HasteAction extends InfuseAction {

    public HasteAction(){
        super(InfuseEffect.HASTE);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), Helpers.pickaxesAndTools, Enchantment.FORTUNE, 5);
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), Helpers.pickaxesAndTools, Enchantment.UNBREAKING, 5);
        Helpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), Helpers.pickaxesAndTools, Enchantment.EFFICIENCY, 10);
    }

    @Override
    protected void onSparked(Player player) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4.5);
        player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(10);
    }

    @Override
    protected void onSparkEnd(Player player) {
        player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(4);
        player.getAttribute(Attribute.PLAYER_BLOCK_BREAK_SPEED).setBaseValue(1);
    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 30, 1));
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 30, 1));
            }
        };
    }
}
