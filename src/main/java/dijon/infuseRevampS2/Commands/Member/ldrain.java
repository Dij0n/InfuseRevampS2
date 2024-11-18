package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.HUD.HUDDisplayer;
import dijon.infuseRevampS2.ItemBehavior.PotionItemStacks;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ldrain implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player player) {

            UUID uuid = player.getUniqueId();
            InfuseEffect effect = PlayerDataManager.getPrimary(uuid);

            if(effect.equals(InfuseEffect.NONE)) {
                if(PlayerDataManager.getSecondary(uuid).equals(InfuseEffect.NONE)){
                    sender.sendMessage(ChatColor.RED + "Incorrect Usage: You have no effect equipped in this slot!");
                    return true;
                }
                rdrain.unequipSecondarySlot(player, uuid, PlayerDataManager.getSecondary(uuid));
                return true;
            }

            unequipPrimarySlot(player, uuid, effect);
        }
        return true;
    }

    public static void unequipPrimarySlot(Player player, UUID uuid, InfuseEffect effect){
        if(PlayerDataManager.isPrimaryActivated(uuid)){
            player.sendMessage(ChatColor.RED + "You can't drain your effect while it's active!");
            return;
        }

        if ((!PlayerDataManager.isPrimarySwapCooldownOver(uuid) || !PlayerDataManager.isPrimaryCooldownOver(uuid))){
            long bigTime = Math.max(PlayerDataManager.getPrimarySwapCooldownLeft(uuid), PlayerDataManager.getPrimaryCooldownLeft(uuid));
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
        PlayerDataManager.setPrimary(uuid, InfuseEffect.NONE);
        effect.getAction().runUnequippedTask(player);
    }

}
