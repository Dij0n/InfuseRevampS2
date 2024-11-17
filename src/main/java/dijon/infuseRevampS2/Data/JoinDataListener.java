package dijon.infuseRevampS2.Data;

import dijon.infuseRevampS2.InfuseRevampS2;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class JoinDataListener implements Listener {

    public JoinDataListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        Bukkit.getLogger().info("Player joined");

        if(PlayerDataManager.isPlayerLoaded(uuid)){
            return;
        }

        Bukkit.getLogger().info("Adding Player");
        PlayerData newPlayer = new PlayerData(uuid, InfuseEffect.NONE, InfuseEffect.NONE);
        PlayerDataManager.addPlayer(newPlayer);

        Bukkit.getLogger().info("Saving");
        PlayerFileManager.saveData();
        PlayerFileManager.loadData();

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        PlayerFileManager.saveData();
    }

}
