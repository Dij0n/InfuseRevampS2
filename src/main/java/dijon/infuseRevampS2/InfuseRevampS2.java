package dijon.infuseRevampS2;

import dijon.infuseRevampS2.data.PlayerDataManager;
import dijon.infuseRevampS2.data.PlayerFileManager;
import dijon.infuseRevampS2.effects.InfuseEffect;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfuseRevampS2 extends JavaPlugin {

    public static InfuseRevampS2 instance;

    @Override
    public void onEnable() {

        instance = this;

        //Manager Initializers
        PlayerDataManager.initialize();
        PlayerFileManager.initialize();
        InfuseEffect.initializeEffects();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
