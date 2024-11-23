package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class trust implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player p) {
            if(args.length != 1){
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Ex. /trust <username>");
                return true;
            }

            Player teammate = Bukkit.getPlayer(args[0]);
            if (teammate == null) {
                sender.sendMessage(ChatColor.RED + "Player is not online/does not exist");
                return true;
            }
            if(teammate.equals(p)){
                sender.sendMessage(ChatColor.RED + "\"omgg im gonna try trusting myself i wonder what happenss\" stfu");
                return true;
            }

            ArrayList<UUID> trustList = PlayerDataManager.getTrustedList(teammate.getUniqueId());
            if(trustList.contains(teammate.getUniqueId())){
                PlayerDataManager.removeTrustedPlayer(p.getUniqueId(), teammate);
                sender.sendMessage(ChatColor.RED + "§l" + teammate.getName() + "§r" + ChatColor.RED + " has been un-trusted");
            }else{
                PlayerDataManager.addTrustedPlayer(p.getUniqueId(), teammate);
                sender.sendMessage(ChatColor.GREEN + "§l" + teammate.getName() + "§r" + ChatColor.GREEN + " has been trusted");
            }
        }

        return true;
    }
}
