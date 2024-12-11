package dijon.infuseRevampS2.Crafting;

import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class CraftingMenuHandler implements Listener {

    public CraftingMenuHandler(){
        Bukkit.getPluginManager().registerEvents(this, InfuseRevampS2.instance);
    }

    @EventHandler
    public void onBrewingOpen(InventoryOpenEvent e){
        if(e.getInventory().getType().equals(InventoryType.BREWING)){
            e.setCancelled(true);
            e.getPlayer().openInventory(createCustomCraftingMenu());
        }
    }




    public Inventory createCustomCraftingMenu(){
        return Bukkit.getServer().createInventory(null, 45, "Craft an Effect!");
    }

}
