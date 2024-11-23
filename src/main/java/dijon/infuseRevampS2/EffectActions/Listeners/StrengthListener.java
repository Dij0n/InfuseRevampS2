package dijon.infuseRevampS2.EffectActions.Listeners;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
import dijon.infuseRevampS2.InfuseRevampS2;
import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;

import java.util.UUID;

public class StrengthListener implements Listener {

    public StrengthListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    //PASSIVES

    //SHIELD COOLDOWN -> 10 SECONDS
    @EventHandler
    public void onShieldAttack(PlayerShieldDisableEvent e){
        if(!(e.getDamager() instanceof Player attacker)) return;
        if(hasEffect(attacker.getUniqueId())){
            e.setCooldown(e.getCooldown() * 2);
        }
    }

    //INSTANT KILL ENTITIES
    @EventHandler
    public void instantKill(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.playerAndMob(e)) return;
        Player player = (Player) e.getDamager();
        LivingEntity mob = (LivingEntity) e.getEntity();
        if(mob.getType().equals(EntityType.VILLAGER) || mob.getType().equals(EntityType.PLAYER)) return;

        if(hasEffect(player.getUniqueId())){
            e.setDamage(e.getDamage() * 2);
        }
    }

    //BOWS HAVE PIERCING
    @EventHandler
    public void onHoldBow(PlayerItemHeldEvent e){
        ListenerHelpers.onHoldSpecialItem(e, InfuseEffect.STRENGTH, ListenerHelpers.bows, Enchantment.PIERCING, 100);
        ListenerHelpers.onNotHoldSpecialItem(e, ListenerHelpers.bows, Enchantment.PIERCING, 100);
    }

    @EventHandler
    public void onDropBow(PlayerDropItemEvent e){
        ListenerHelpers.onDropSpecialItem(e, ListenerHelpers.bows, Enchantment.PIERCING, 100);
    }

    @EventHandler
    public void onMoveBow(InventoryClickEvent e){
        ListenerHelpers.onMoveSpecialItem(e, ListenerHelpers.bows, Enchantment.PIERCING, 100);
    }

    //SPARKS

    //ALL CRITS
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e){
        if(!ListenerHelpers.bothPlayers(e)) return;
        if(!sparked(e.getDamager().getUniqueId())) return;

        Player player = (Player) e.getDamager();
        Player victim = (Player) e.getEntity();

        if(e.isCritical()) return;

        e.setDamage(e.getDamage() * 1.5);
        player.getWorld().spawnParticle(Particle.CRIT, victim.getEyeLocation(), 20);
        player.playSound(player, Sound.ENTITY_PLAYER_ATTACK_CRIT, 10, 1);
    }

    //HELPERS
    public boolean hasEffect(UUID uuid){
        return PlayerDataManager.hasEffect(uuid, InfuseEffect.STRENGTH);
    }

    public boolean sparked(UUID uuid){
        return PlayerDataManager.hasEffectSparked(uuid, InfuseEffect.STRENGTH);
    }


}
