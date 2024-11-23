package dijon.infuseRevampS2;

import dijon.infuseRevampS2.Commands.Member.*;
import dijon.infuseRevampS2.Commands.OP.infusegive;
import dijon.infuseRevampS2.Commands.OP.infusegivetabcomplete;
import dijon.infuseRevampS2.Commands.OP.setcooldown;
import dijon.infuseRevampS2.Commands.OP.setduration;
import dijon.infuseRevampS2.Data.CooldownFileManager;
import dijon.infuseRevampS2.Data.JoinDataListener;
import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.Data.PlayerFileManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.*;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.GenericListener;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.ListenerHelpers;
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
        ListenerHelpers.initialize();

        //Listeners
        new JoinDataListener();
        new DrinkListener();

        new GenericListener();
        new StrengthListener();
        new EmeraldListener();
        new HasteListener();
        new RegenListener();
        new HeartListener();
        new SpeedListener();

        //HUD
        new HUDDisplayer().runTaskTimer(this, 0, 2);

        //Commands
        this.getCommand("infusegive").setExecutor(new infusegive());
        this.getCommand("ldrain").setExecutor(new ldrain());
        this.getCommand("rdrain").setExecutor(new rdrain());
        this.getCommand("lspark").setExecutor(new lspark());
        this.getCommand("rspark").setExecutor(new rspark());
        this.getCommand("setcooldown").setExecutor(new setcooldown());
        this.getCommand("setduration").setExecutor(new setduration());
        //this.getCommand("swap").setExecutor(new swap());
        this.getCommand("trust").setExecutor(new trust());
        this.getCommand("trusted").setExecutor(new trusted());

        this.getCommand("infusegive").setTabCompleter(new infusegivetabcomplete());
        this.getCommand("setcooldown").setTabCompleter(new infusegivetabcomplete());
        this.getCommand("setduration").setTabCompleter(new infusegivetabcomplete());


    }

    @Override
    public void onDisable() {
        PlayerFileManager.saveData();
        CooldownFileManager.saveCooldowns();
    }
}
