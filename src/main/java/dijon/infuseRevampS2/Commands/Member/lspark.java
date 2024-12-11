package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerData;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class lspark implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length != 0) return true;
            if(p.getGameMode().equals(GameMode.SPECTATOR)) return true;

            if(PlayerDataManager.getPrimary(p.getUniqueId()).equals(InfuseEffect.NONE)) {
                if(PlayerDataManager.getSecondary(p.getUniqueId()).equals(InfuseEffect.NONE)){
                    return true;
                }
                rspark.onSecondaryActivateCommand(p);
                return true;
            }
            onPrimaryActivateCommand(p);
            return true;
        }
        return true;
    }

    public static void onPrimaryActivateCommand(Player p){
        UUID uuid = p.getUniqueId();

        if(PlayerDataManager.getPrimary(uuid).equals(InfuseEffect.NONE)) return;
        if(PlayerDataManager.isPrimaryActivated(uuid)) return;
        if(!PlayerDataManager.isPrimaryCooldownOver(uuid)) return;
        if(PlayerDataManager.getPrimary(uuid).equals(PlayerDataManager.getSecondary(uuid)) && PlayerDataManager.isSecondaryActivated(uuid)) return;

        PlayerDataManager.setPrimaryActive(uuid, true);
        PlayerDataManager.setLastPrimaryActivation(uuid, System.currentTimeMillis());
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1F, 2F);
        p.spawnParticle(Particle.ENTITY_EFFECT, p.getLocation().add(new Vector(0, 1, 0)), 40, 0.5, 0.8, 0.5, 1, Color.fromRGB(PlayerDataManager.getPrimary(uuid).getRawColor()));


        PlayerDataManager.getPrimary(uuid).getAction().runSparkTask(p, true);

    }

}
