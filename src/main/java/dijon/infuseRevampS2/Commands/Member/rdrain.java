package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.HUD.HUDDisplayer;
import dijon.infuseRevampS2.ItemBehavior.PotionItemStacks;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class rdrain implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {

            UUID uuid = player.getUniqueId();
            InfuseEffect effect = PlayerDataManager.getSecondary(uuid);

            if(effect.equals(InfuseEffect.NONE)) {
                if(PlayerDataManager.getPrimary(uuid).equals(InfuseEffect.NONE)){
                    sender.sendMessage(ChatColor.RED + "Incorrect Usage: You have no effect equipped in this slot!");
                    return true;
                }
                ldrain.unequipPrimarySlot(player, uuid, PlayerDataManager.getPrimary(uuid));
                return true;
            }

            unequipSecondarySlot(player, uuid, effect);
        }
        return true;
    }

    public static void unequipSecondarySlot(Player player, UUID uuid, InfuseEffect effect){
        if(PlayerDataManager.isSecondaryActivated(uuid)){
            player.sendMessage(ChatColor.RED + "You can't drain your effect while it's active!");
            return;
        }

        if ((!PlayerDataManager.isSecondarySwapCooldownOver(uuid) || !PlayerDataManager.isSecondaryCooldownOver(uuid))){
            long bigTime = Math.max(PlayerDataManager.getSecondarySwapCooldownLeft(uuid), PlayerDataManager.getSecondaryCooldownLeft(uuid));
            player.sendMessage(ChatColor.RED + "You can drain your effect in " + HUDDisplayer.toMinutes(bigTime)); //Swap timer
            return;
        }

        ItemStack effectItem = PotionItemStacks.getItemStack(effect);

        if (player.getInventory().firstEmpty() == -1){
            player.getWorld().dropItemNaturally(player.getLocation(), effectItem);
        }else{
            player.getInventory().addItem(effectItem);
        }
        player.playSound(player.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 0.9F);
        effect.getAction().runUnequippedTask(player);
        PlayerDataManager.setSecondary(uuid, InfuseEffect.NONE);
    }

}
