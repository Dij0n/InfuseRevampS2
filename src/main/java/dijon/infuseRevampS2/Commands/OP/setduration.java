package dijon.infuseRevampS2.Commands.OP;

import dijon.infuseRevampS2.Data.CooldownFileManager;
import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setduration implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player && sender.isOp()) {
            Player player = (Player) sender;

            if(args.length != 2){
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Ex. /setduration fire 10");
                return true;
            }

            int duration;

            try {
                duration = Integer.parseInt(args[1]);
            } catch(NumberFormatException | NullPointerException e) {
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Ex. /setduration fire 10");
                return true;
            }

            InfuseEffect effect = PlayerFileManager.parseEffect(args[0]);

            if(effect.equals(InfuseEffect.NONE)){
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Ex. /setduration fire 10");
                return true;
            }

            effect.setSparkDuration(duration);
            CooldownFileManager.masterDurationList.put(effect, duration);
            CooldownFileManager.saveCooldowns();
            sender.sendMessage(ChatColor.GREEN + "§l" + effect.getName() + "§r" + ChatColor.GREEN + " duration set to " + "§l" + args[1] + " seconds");

        }else{
            sender.sendMessage(ChatColor.RED + "You aren't opped!! Loser!! L!!");
        }
        return true;
    }
}
