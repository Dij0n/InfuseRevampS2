package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Spawnables.Runnables.ThunderSpark;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ThunderAction extends InfuseAction {

    public ThunderAction(){
        super(InfuseEffect.THUNDER);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {

    }

    @Override
    protected void onSparked(Player player) {
        new ThunderSpark(player);
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
