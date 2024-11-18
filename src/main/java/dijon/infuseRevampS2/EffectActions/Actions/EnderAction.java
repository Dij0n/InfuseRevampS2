package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class EnderAction extends InfuseAction {

    public EnderAction(){
        super(InfuseEffect.ENDER);
    }

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
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("ender normal :)");
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("ender spark! >:)");
            }
        };
    }
}
