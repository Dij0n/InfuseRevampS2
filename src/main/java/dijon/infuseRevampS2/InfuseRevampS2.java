package dijon.infuseRevampS2;

import dijon.infuseRevampS2.Commands.Member.ldrain;
import dijon.infuseRevampS2.Commands.Member.lspark;
import dijon.infuseRevampS2.Commands.Member.rdrain;
import dijon.infuseRevampS2.Commands.Member.rspark;
import dijon.infuseRevampS2.Commands.OP.infusegive;
import dijon.infuseRevampS2.Commands.OP.infusegivetabcomplete;
import dijon.infuseRevampS2.Data.CooldownFileManager;
import dijon.infuseRevampS2.Data.JoinDataListener;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.HUD.HUDDisplayer;
import dijon.infuseRevampS2.ItemBehavior.DrinkListener;
import dijon.infuseRevampS2.ItemBehavior.PotionItemStacks;
import org.bukkit.plugin.java.JavaPlugin;

public final class InfuseRevampS2 extends JavaPlugin {

    public static InfuseRevampS2 instance;

    @Override
    public void onEnable() {

        instance = this;

        //Manager Initializers
        InfuseEffect.initializeList();
        PlayerDataManager.initialize();
        PlayerFileManager.initialize();
        CooldownFileManager.initializeTimes();
        InfuseEffect.initializeEffects();
        PotionItemStacks.initializeLore();

        //Listeners
        new JoinDataListener();
        new DrinkListener();

        //HUD
        new HUDDisplayer().runTaskTimer(this, 0, 2);

        //Commands
        this.getCommand("infusegive").setExecutor(new infusegive());
        this.getCommand("ldrain").setExecutor(new ldrain());
        this.getCommand("rdrain").setExecutor(new rdrain());
        this.getCommand("lspark").setExecutor(new lspark());
        this.getCommand("rspark").setExecutor(new rspark());

        this.getCommand("infusegive").setTabCompleter(new infusegivetabcomplete());

    }

    @Override
    public void onDisable() {
        PlayerFileManager.saveData();
        CooldownFileManager.saveCooldowns();
    }
}
