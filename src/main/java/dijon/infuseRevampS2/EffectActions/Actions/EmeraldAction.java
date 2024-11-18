package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class EmeraldAction extends InfuseAction {

    public EmeraldAction(){
        super(InfuseEffect.EMERALD);
    }

    @Override
    protected void onEquip(Player player) {
        player.sendMessage("emerlad wooo");
    }

    @Override
    protected void onUnequipped(Player player) {
        player.sendMessage("emerlad booo");
    }

    @Override
    protected void onSparked(Player player) {
        player.sendMessage("emerlad spark!!!");
    }

    @Override
    protected void onSparkEnd(Player player) {
        player.sendMessage("emerlad spark no :((");
    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("emerald normal :)");
            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                player.sendMessage("emerald spark! >:)");
            }
        };
    }
}
