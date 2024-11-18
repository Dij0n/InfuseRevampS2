package dijon.infuseRevampS2.ItemBehavior;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.HUD.HUDDisplayer;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class DrinkListener implements Listener {

    public DrinkListener() {
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }


    @EventHandler
    public void equipCheck(PlayerItemConsumeEvent e){

        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        if(!e.getItem().getPersistentDataContainer().has(new NamespacedKey(InfuseRevampS2.instance, "InfuseData"))) return;

        String effectID = e.getItem().getPersistentDataContainer().get(new NamespacedKey(InfuseRevampS2.instance, "InfuseData"), PersistentDataType.STRING);
        InfuseEffect effect = PlayerFileManager.parseEffect(effectID);
        if(effect == InfuseEffect.NONE) return;

        if(PlayerDataManager.getSecondary(uuid) == InfuseEffect.NONE){
            applyToSecondarySlot(p, effect, e);
        }else{
            applyToPrimarySlot(p, effect, e);
        }
    }

    public void applyToPrimarySlot(Player p, InfuseEffect effect, PlayerItemConsumeEvent e){
        InfuseEffect oldEffect = PlayerDataManager.getPrimary(p.getUniqueId());

        boolean swapCooldownStillOn = !PlayerDataManager.isPrimarySwapCooldownOver(p.getUniqueId());
        boolean cooldownStillOn = !PlayerDataManager.isPrimaryCooldownOver(p.getUniqueId());
        boolean somethingInSlot = PlayerDataManager.getPrimary(p.getUniqueId()) != (InfuseEffect.NONE);

        if ((swapCooldownStillOn || cooldownStillOn) && somethingInSlot){
            long bigTime = Math.max(PlayerDataManager.getPrimarySwapCooldownLeft(p.getUniqueId()), PlayerDataManager.getPrimaryCooldownLeft(p.getUniqueId()));
            p.sendMessage(ChatColor.RED + "You can swap your effect in §l" + HUDDisplayer.toMinutes(bigTime)); //Swap timer
            e.setCancelled(true);
            return;
        }

        p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount()-1);

        e.getItem().setAmount(e.getItem().getAmount() - 1);

        if (somethingInSlot){
            ItemStack oldEffectItem = PotionItemStacks.getItemStack(oldEffect);
            p.getInventory().addItem(oldEffectItem);
            PlayerDataManager.setLastPrimarySwap(p.getUniqueId(), System.currentTimeMillis());
            oldEffect.getAction().runUnequippedTask(p);
        }

        p.playSound(p.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 1.3F);
        PlayerDataManager.setPrimary(p.getUniqueId(), effect);
        PlayerDataManager.setLastPrimaryActivation(p.getUniqueId(), 0);
        effect.getAction().runEquipTask(p);
    }
    public void applyToSecondarySlot(Player p, InfuseEffect effect, PlayerItemConsumeEvent e){
        InfuseEffect oldEffect = PlayerDataManager.getSecondary(p.getUniqueId());

        boolean swapCooldownStillOn = !PlayerDataManager.isSecondarySwapCooldownOver(p.getUniqueId());
        boolean cooldownStillOn = !PlayerDataManager.isSecondaryCooldownOver(p.getUniqueId());
        boolean somethingInSlot = PlayerDataManager.getSecondary(p.getUniqueId()) != (InfuseEffect.NONE);

        if ((swapCooldownStillOn || cooldownStillOn) && somethingInSlot){
            long bigTime = Math.max(PlayerDataManager.getSecondarySwapCooldownLeft(p.getUniqueId()), PlayerDataManager.getSecondaryCooldownLeft(p.getUniqueId()));
            p.sendMessage(ChatColor.RED + "You can swap your effect in §l" + HUDDisplayer.toMinutes(bigTime)); //Swap timer
            e.setCancelled(true);
            return;
        }

        p.getInventory().getItemInHand().setAmount(p.getInventory().getItemInHand().getAmount()-1);

        if (somethingInSlot){
            ItemStack oldEffectItem = PotionItemStacks.getItemStack(oldEffect);
            p.getInventory().addItem(oldEffectItem);
            PlayerDataManager.setLastSecondarySwap(p.getUniqueId(), System.currentTimeMillis());
            oldEffect.getAction().runUnequippedTask(p);
        }

        PlayerDataManager.setSecondary(p.getUniqueId(), effect);
        PlayerDataManager.setLastSecondaryActivation(p.getUniqueId(), 0);
        p.playSound(p.getLocation(), Sound.BLOCK_BREWING_STAND_BREW, 1F, 1.3F);
        effect.getAction().runEquipTask(p);
    }
}
