package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.FoodValues;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SpeedListener implements Listener {

    public static final HashMap<UUID, Integer> speedLevelMap = new HashMap<>();
    public static final HashMap<UUID, Integer> speedCooldownMap = new HashMap<>();

    public SpeedListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.playerAndMob(e)) return;
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        Player attacker = (Player) e.getDamager();
        if(attacker.getAttackCooldown() < 1) return;
        UUID uuid = e.getDamager().getUniqueId();
        speedLevelMap.put(uuid, speedLevelMap.getOrDefault(uuid, 0) + 1); //Increase Speed Level
        if(speedLevelMap.get(uuid) > 5) speedLevelMap.put(uuid, 5);
        speedCooldownMap.putIfAbsent(uuid, 20); //Initialize Cooldown if not already initialized
        speedCooldownMap.put(uuid, speedCooldownMap.get(uuid) + 20); //Increase cooldown by 20 ticks
        BukkitRunnable task = new BukkitRunnable() {
            int counter = 20;
            @Override
            public void run() {
                speedCooldownMap.put(uuid, speedCooldownMap.get(uuid) - 1);
                counter--;
                if (counter <= 0) {
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(InfuseRevampS2.instance, 0, 1);
    }

    @EventHandler
    public void onAttackInvFrames(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.bothPlayers(e)) return;
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        Player attacker = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();
        if(attacker.getAttackCooldown() < 1) return;

        victim.setNoDamageTicks(5);

    }

    @EventHandler
    public void onHit(EntityDamageEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        if(!hasEffect(e.getEntity().getUniqueId())) return;
        UUID uuid = e.getEntity().getUniqueId();
        speedLevelMap.put(uuid, speedLevelMap.getOrDefault(uuid, 0) - 1); //Increase Speed Level
        if(speedLevelMap.get(uuid) <= 0){
            speedLevelMap.put(uuid, 0);
            return;
        }
        speedCooldownMap.putIfAbsent(uuid, 20); //Initialize Cooldown if not already initialized
        speedCooldownMap.put(uuid, speedCooldownMap.get(uuid) + 20); //Increase cooldown by 40 ticks
        BukkitRunnable task = new BukkitRunnable() {
            int counter = 20;
            @Override
            public void run() {
                speedCooldownMap.put(uuid, speedCooldownMap.get(uuid) - 1);
                counter--;
                if (counter <= 0) {
                    this.cancel();
                }
            }
        };
        task.runTaskTimer(InfuseRevampS2.instance, 0, 1);
    }



    @EventHandler
    public void onBowShot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!hasEffect(p.getUniqueId())) return;
        if(!(e.getProjectile() instanceof Arrow arrow)) return;

        if(arrow.getVelocity().length() > 1.5){
            Vector velClone = arrow.getVelocity().clone();
            double scalar = 2.96 / velClone.length();
            velClone.multiply(scalar);
            arrow.setVelocity(velClone);
            arrow.setCritical(true);
        }
    }

    @EventHandler
    public void onHoldCrossbow(PlayerItemHeldEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;
        if(e.getPlayer().getInventory().getItem(e.getNewSlot()) != null){
            if(e.getPlayer().getInventory().getItem(e.getNewSlot()).getType().equals(Material.CROSSBOW) && e.getPlayer().getInventory().getItem(e.getNewSlot()).getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3) return;
        }
        ListenerHelpers.onHoldSpecialItem(e, InfuseEffect.SPEED, Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
        ListenerHelpers.onNotHoldSpecialItem(e, Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
    }

    @EventHandler
    public void onDropCrossbow(PlayerDropItemEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;
        if(e.getItemDrop().getItemStack().getType().equals(Material.CROSSBOW) && e.getItemDrop().getItemStack().getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3) return;
        ListenerHelpers.onDropSpecialItem(e, Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
    }

    @EventHandler
    public void onMoveCrossbow(InventoryClickEvent e){
        if(!hasEffect(e.getWhoClicked().getUniqueId())) return;
        if(e.getCursor().getType().equals(Material.CROSSBOW) && e.getCursor().getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3) return;
        if(e.getCurrentItem() != null){
            if(e.getCurrentItem().getType().equals(Material.CROSSBOW) && e.getCurrentItem().getEnchantmentLevel(Enchantment.QUICK_CHARGE) == 3) return;
        }
        ListenerHelpers.onMoveSpecialItem(e, Collections.singleton(Material.CROSSBOW), Enchantment.QUICK_CHARGE, 2);
    }


    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.SPEED);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.SPEED);
    }


}
