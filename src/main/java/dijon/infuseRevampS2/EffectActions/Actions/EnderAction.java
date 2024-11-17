package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class EnderAction extends InfuseAction {

    @Override
    protected void onEquip(Player player) {
        player.sendMessage("ender wooo");
    }

    @Override
    protected void onUnequipped(Player player) {
        player.sendMessage("ender booo");
    }

    @Override
    protected void onSparked(Player player) {

    }

    @Override
    protected void onSparkEnd(Player player) {

    }

    @Override
    protected BukkitRunnable createStandardInterim() {
        return null;
    }

    @Override
    protected BukkitRunnable createSparkedInterim() {
        return null;
    }
}
