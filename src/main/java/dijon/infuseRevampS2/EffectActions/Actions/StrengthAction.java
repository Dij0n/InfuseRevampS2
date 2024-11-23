package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class StrengthAction extends InfuseAction {

    public StrengthAction(){
        super(InfuseEffect.STRENGTH);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {
        ListenerHelpers.generalRemoveSpecialItem(player.getInventory().getItemInMainHand(), ListenerHelpers.bows, Enchantment.PIERCING, 100);
        ListenerHelpers.generalRemoveSpecialItem(player.getInventory().getItemInOffHand(), ListenerHelpers.bows, Enchantment.PIERCING, 100);
    }

    @Override
    protected void onSparked(Player player) {

    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return null;
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return null;
    }
}
