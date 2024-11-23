package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class trusted implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {

            p.sendMessage(Component.text("Online Trusted Players: ").color(TextColor.color(0,255,0)));

            for (UUID uuid : PlayerDataManager.getTrustedList(p.getUniqueId())){
                Player teammate = Bukkit.getPlayer(uuid);
                if(teammate != null){
                    p.sendMessage(Component.text("-" + teammate.getName()).color(TextColor.color(0,255,0)));
                }
            }

            p.sendMessage(Component.text("Offline Trusted Players: ").color(TextColor.color(255,0,0)));

            for (UUID uuid : PlayerDataManager.getTrustedList(p.getUniqueId())){
                Player teammate = Bukkit.getPlayer(uuid);
                if(teammate == null){
                    p.sendMessage(Component.text("-" + uuid).color(TextColor.color(255,0,0)));
                }
            }

        }
        return true;
    }
}
