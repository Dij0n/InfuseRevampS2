package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class swap implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length != 0) return true;
            if(p.getGameMode().equals(GameMode.SPECTATOR)) return true;
            InfuseEffect primary = PlayerDataManager.getPrimary(p.getUniqueId());
            InfuseEffect secondary = PlayerDataManager.getSecondary(p.getUniqueId());
            PlayerDataManager.setPrimary(p.getUniqueId(), secondary);
            PlayerDataManager.setSecondary(p.getUniqueId(), primary);
            return true;
        }
        return true;
    }

}
