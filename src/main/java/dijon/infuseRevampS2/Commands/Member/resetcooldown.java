package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class resetcooldown implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length != 0) return true;
            if(p.getGameMode().equals(GameMode.SPECTATOR)) return true;
            PlayerDataManager.setLastPrimaryActivation(p.getUniqueId(), 0);
            PlayerDataManager.setLastSecondaryActivation(p.getUniqueId(), 0);
            return true;
        }
        return true;
    }

}
