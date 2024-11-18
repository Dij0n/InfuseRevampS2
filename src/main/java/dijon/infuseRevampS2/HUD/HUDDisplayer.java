package dijon.infuseRevampS2.HUD;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.Actions.NoneAction;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class HUDDisplayer extends BukkitRunnable {
    @Override
    public void run() {
        for(Player p : Bukkit.getOnlinePlayers()){
            p.sendActionBar(getText(p));
        }
    }

    public Component getText(Player p){
        InfuseEffect primary = PlayerDataManager.getPrimary(p.getUniqueId());
        InfuseEffect secondary = PlayerDataManager.getSecondary(p.getUniqueId());
        Component pTimeString = null;
        Component pString;
        Component sString;
        Component sTimeString = null;

        //Cooldown Text
        if(!PlayerDataManager.isPrimaryCooldownOver(p.getUniqueId())){
            pTimeString = Component.text(toMinutes(PlayerDataManager.getPrimaryCooldownLeft(p.getUniqueId())) + "  ").color(TextColor.color(0xffffff)).decorate(TextDecoration.BOLD);
        }
        if(!PlayerDataManager.isSecondaryCooldownOver(p.getUniqueId())){
            sTimeString = Component.text("  " + toMinutes(PlayerDataManager.getSecondaryCooldownLeft(p.getUniqueId()))).color(TextColor.color(0xffffff)).decorate(TextDecoration.BOLD);
        }

        //Spark/Duration Text
        if(PlayerDataManager.isPrimaryActivated(p.getUniqueId())){
            pString = Component.text(primary.getTextureSpark() + " ").color(TextColor.color(0xffffff)).decoration(TextDecoration.BOLD, false);
            pTimeString = Component.text(toMinutes(PlayerDataManager.getPrimarySparkDurationLeft(p.getUniqueId())) + "  ").color(primary.getColor()).decorate(TextDecoration.BOLD);
        }else{
            pString = Component.text(primary.getTexture() + " ").color(TextColor.color(0xffffff)).decoration(TextDecoration.BOLD, false);
        }

        if(PlayerDataManager.isSecondaryActivated(p.getUniqueId())){
            sString = Component.text(secondary.getTextureSpark()).color(TextColor.color(0xffffff)).decoration(TextDecoration.BOLD, false);
            sTimeString = Component.text("  " + toMinutes(PlayerDataManager.getSecondarySparkDurationLeft(p.getUniqueId()))).color(secondary.getColor()).decorate(TextDecoration.BOLD);
        }else{
            sString = Component.text(secondary.getTexture()).color(TextColor.color(0xffffff)).decoration(TextDecoration.BOLD, false);
        }

        //Padding
        if (pTimeString != null && sTimeString == null){
            sTimeString = Component.text("       ");
        }
        if (sTimeString != null && pTimeString == null){
            pTimeString = Component.text("       ");
        }

        //Safety null checks
        if(pTimeString == null){
            pTimeString = Component.text("");
        }
        if(sTimeString == null){
            sTimeString = Component.text("");
        }


        return pTimeString.append(pString).append(sString).append(sTimeString);

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
