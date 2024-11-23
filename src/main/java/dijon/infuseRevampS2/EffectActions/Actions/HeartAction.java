package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HeartAction extends InfuseAction {

    public HeartAction(){
        super(InfuseEffect.HEART);
    }

    @Override
    protected void onEquip(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
        player.setHealth(30);
    }

    @Override
    protected void onUnequipped(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
    }

    @Override
    protected void onSparked(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(40);
        player.setHealth(40);
    }

    @Override
    protected void onSparkEnd(Player player) {
        player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(30);
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
