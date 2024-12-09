package dijon.infuseRevampS2.EffectActions.Listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class OceanListener implements Listener {

    public HashMap<UUID, Integer> specialDrowning;

    public OceanListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onTridentLand(ProjectileHitEvent e){
        if(!(e.getEntity() instanceof Trident trident)) return;
        if(!(e.getHitEntity() instanceof LivingEntity livingEntity)) return;
        if(!(e.getEntity().getShooter() instanceof Player player)) return;
        if(!hasEffect(player.getUniqueId())) return;

        if(e.getHitEntity() instanceof Player opp){
            if(opp.isBlocking()){
                return;
            }
        }

        Vector tridentVel = trident.getVelocity();
        tridentVel.multiply(-1);
        tridentVel.setY(Math.abs(tridentVel.getY()) + 1);
        tridentVel.multiply(1.5);
        livingEntity.setVelocity(tridentVel);
    }



    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.OCEAN);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.OCEAN);
    }

}
