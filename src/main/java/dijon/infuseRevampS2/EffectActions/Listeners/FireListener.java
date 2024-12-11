package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.SmeltingValues;
import dijon.infuseRevampS2.EffectActions.Spawnables.Runnables.CustomFire;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class FireListener implements Listener {

    public static final HashMap<UUID, CustomFire> customFireHolder = new HashMap<>();

    public FireListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void lavaStop(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) return;
        if(!hasEffect(player.getUniqueId())) return;
        if(!(e.getCause().equals(EntityDamageEvent.DamageCause.FALL) || e.getCause().equals(EntityDamageEvent.DamageCause.FLY_INTO_WALL))) return;

        Block block = player.getWorld().getBlockAt(player.getLocation());
        if(block.getType().equals(Material.LAVA)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBreakSmelt(BlockDropItemEvent e){
        if(!hasEffect(e.getPlayer().getUniqueId())) return;

        ItemStack pickaxe = e.getPlayer().getInventory().getItemInMainHand();
        if(pickaxe.getItemMeta() == null) return;
        if(pickaxe.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) return;

        for(Item item : e.getItems()){
            if(SmeltingValues.isSmeltable(item.getItemStack().getType())){
                ItemStack itemStack = item.getItemStack();
                itemStack.setType(SmeltingValues.getSmelted(itemStack.getType()));
                item.setItemStack(itemStack);
                e.getPlayer().getWorld().playSound(e.getPlayer().getLocation(), Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 1);
            }
        }
    }

    @EventHandler
    public void onAttackMob(EntityDamageByEntityEvent e){
        if(!hasEffect(e.getDamager().getUniqueId())) return;
        if(Helpers.bothPlayers(e)){
            if(Helpers.tenthHit(e)){
                LivingEntity livingEntity = (LivingEntity) e.getEntity();
                applyOrRefreshCustomFire(livingEntity);
            }
            return;
        }
        if(Helpers.playerAndMob(e)){
            LivingEntity livingEntity = (LivingEntity) e.getEntity();
            applyOrRefreshCustomFire(livingEntity);
        }
    }

    @EventHandler
    public void onBowShot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player p)) return;
        if(!hasEffect(p.getUniqueId())) return;
        if(!(e.getProjectile() instanceof Arrow arrow)) return;

        if(arrow.getVelocity().length() > 2.9){
            arrow.setFireTicks(200);
        }
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.FIRE);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.FIRE);
    }


    public static void applyOrRefreshCustomFire(LivingEntity livingEntity){
        if(!customFireHolder.containsKey(livingEntity.getUniqueId())){
            CustomFire fire = new CustomFire(livingEntity);
            customFireHolder.putIfAbsent(livingEntity.getUniqueId(), fire);
        }else{
            customFireHolder.get(livingEntity.getUniqueId()).reset();
        }
    }

}
