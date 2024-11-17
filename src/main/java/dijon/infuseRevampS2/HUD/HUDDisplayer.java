package dijon.infuseRevampS2.HUD;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HUDDisplayer extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            InfuseEffect primary = PlayerDataManager.getPrimary(p.getUniqueId());
            InfuseEffect secondary = PlayerDataManager.getSecondary(p.getUniqueId());
            p.sendActionBar(primary.getName() + " " + secondary.getName());
        }
    }

    public static String toMinutes(long milli){
        long seconds = milli/1000;
        long minutes = (long) Math.floor((double) seconds /60);
        long minutesLeft = seconds - (minutes * 60);

        String sMinutes = String.valueOf(minutes);
        String sMinutesLeft = String.valueOf(minutesLeft);

        if(minutesLeft < 10) sMinutesLeft = "0" + minutesLeft;

        return sMinutes + ":" + sMinutesLeft;
    }
}
