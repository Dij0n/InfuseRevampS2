package dijon.infuseRevampS2.Commands.Member;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class rspark implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player p){
            if(strings.length != 0) return true;
            if(p.getGameMode().equals(GameMode.SPECTATOR)) return true;
            if(PlayerDataManager.getSecondary(p.getUniqueId()).equals(InfuseEffect.NONE)) {
                if(PlayerDataManager.getPrimary(p.getUniqueId()).equals(InfuseEffect.NONE)){
                    return true;
                }
                lspark.onPrimaryActivateCommand(p);
                return true;
            }
            onSecondaryActivateCommand(p);
            return true;
        }
        return true;
    }

    public static void onSecondaryActivateCommand(Player p){
        UUID uuid = p.getUniqueId();

        if(PlayerDataManager.getSecondary(uuid).equals(InfuseEffect.NONE)) return;
        if(PlayerDataManager.isSecondaryActivated(uuid)) return;
        if(!PlayerDataManager.isSecondaryCooldownOver(uuid)) return;
        if(PlayerDataManager.getPrimary(uuid).equals(PlayerDataManager.getSecondary(uuid)) && PlayerDataManager.isPrimaryActivated(uuid)) return;

        PlayerDataManager.setSecondaryActive(uuid, true);
        PlayerDataManager.setLastSecondaryActivation(uuid, System.currentTimeMillis());
        p.getWorld().playSound(p.getLocation(), Sound.BLOCK_BEACON_POWER_SELECT, 1F, 2F);
        p.spawnParticle(Particle.ENTITY_EFFECT, p.getLocation().add(new Vector(0, 1, 0)), 40, 0.5, 0.8, 0.5, 1, Color.fromRGB(PlayerDataManager.getSecondary(uuid).getRawColor()));

        PlayerDataManager.getSecondary(uuid).getAction().runSparkTask(p, false);

    }

}
