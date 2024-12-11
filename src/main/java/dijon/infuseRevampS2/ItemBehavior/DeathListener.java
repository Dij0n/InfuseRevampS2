package dijon.infuseRevampS2.ItemBehavior;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.HUD.HUDDisplayer;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class DeathListener implements Listener {

    public DeathListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }


    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        PlayerDataManager.setPrimaryActive(e.getPlayer().getUniqueId(), false);
        PlayerDataManager.setSecondaryActive(e.getPlayer().getUniqueId(), false);
        PlayerDataManager.getPrimary(e.getPlayer().getUniqueId()).getAction().runSparkEndTask(e.getPlayer());
        PlayerDataManager.getSecondary(e.getPlayer().getUniqueId()).getAction().runSparkEndTask(e.getPlayer());
        PlayerDataManager.setLastPrimaryActivation(e.getPlayer().getUniqueId(), 0);
        PlayerDataManager.setLastSecondaryActivation(e.getPlayer().getUniqueId(), 0);
    }


}
