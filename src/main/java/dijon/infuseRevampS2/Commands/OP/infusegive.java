package dijon.infuseRevampS2.Commands.OP;

import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.ItemBehavior.PotionItemStacks;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class infusegive implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player && sender.isOp()) {
            if(args.length != 1){
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Ex. /infusegive fire");
                return true;
            }

            InfuseEffect effect = PlayerFileManager.parseEffect(args[0]);
            if(effect == InfuseEffect.NONE){
                sender.sendMessage(ChatColor.RED + "Incorrect usage: Not an effect");
                return true;
            }

            ItemStack effectItem = PotionItemStacks.getItemStack(effect);
            player.getInventory().addItem(effectItem);
        }
        return true;
    }
}
