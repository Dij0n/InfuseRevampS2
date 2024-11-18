package dijon.infuseRevampS2.EffectActions;

import dijon.infuseRevampS2.Data.CooldownFileManager;
import dijon.infuseRevampS2.EffectActions.Actions.*;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;

import java.util.ArrayList;

public enum InfuseEffect {

    NONE("none", "", "", 0x0),
    STRENGTH("strength", "\uE002", "\uE014", 0xE0191D),
    HEART("heart", "\uE003", "\uE015", 0xA61719),
    HASTE("haste", "\uE004", "\uE016", 0xFFDC1E),
    INVIS("invis", "\uE005", "\uE017", 0xB895FF),
    FEATHER("feather", "\uE006", "\uE018", 0xAE60D),
    FROST("frost", "\uE007", "\uE019", 0x3FE4FF),
    THUNDER("thunder", "\uE008", "\uE020", 0xE5E349),
    REGEN("regen", "\uE009", "\uE021", 0xFFB6AC),
    OCEAN("ocean", "\uE010", "\uE022", 0x137BFF),
    FIRE("fire", "\uE011", "\uE023", 0xFF5103),
    EMERALD("emerald", "\uE012", "\uE024", 0x1EFF12),
    SPEED("speed", "\uE013", "\uE025", 0x5EE1FF),
    ENDER("ender", "\uE026", "\uE027", 0x69248E);

    final String name;
    final String texture;
    final String textureSpark;
    final TextColor color;
    final int rawColor;
    InfuseAction action;
    int sparkDuration;
    int sparkCooldown;

    final static ArrayList<InfuseEffect> effectList = new ArrayList<>();

    InfuseEffect(String name, String texture, String textureSpark, int rawColor){
        this.name = name;
        this.texture = texture;
        this.textureSpark = textureSpark;
        this.color = TextColor.color(rawColor);
        this.rawColor = rawColor;

        //Initialized Later
        this.sparkDuration = 0;
        this.sparkCooldown = 0;
        this.action = null;
    }

    public String getName() {
        return name;
    }
    public String getTexture() {
        return texture;
    }
    public TextColor getColor() {
        return color;
    }
    public int getRawColor() {
        return rawColor;
    }
    public String getTextureSpark() {
        return textureSpark;
    }
    public int getSparkDuration() {
        return sparkDuration;
    }
    public int getSparkCooldown() {
        return sparkCooldown;
    }
    public InfuseAction getAction(){
        return action;
    }

    public void setSparkDuration(int time){
        this.sparkDuration = time;
    }
    public void setSparkCooldown(int time){
        this.sparkCooldown = time;
    }
    public void setAction(InfuseAction action){
        this.action = action;
    }

    public static void initializeList(){
        effectList.add(InfuseEffect.NONE);
        effectList.add(InfuseEffect.STRENGTH);
        effectList.add(InfuseEffect.HEART);
        effectList.add(InfuseEffect.HASTE);
        effectList.add(InfuseEffect.INVIS);
        effectList.add(InfuseEffect.FEATHER);
        effectList.add(InfuseEffect.FROST);
        effectList.add(InfuseEffect.THUNDER);
        effectList.add(InfuseEffect.REGEN);
        effectList.add(InfuseEffect.OCEAN);
        effectList.add(InfuseEffect.FIRE);
        effectList.add(InfuseEffect.EMERALD);
        effectList.add(InfuseEffect.SPEED);
        effectList.add(InfuseEffect.ENDER);
    }

    public static void initializeEffects(){
        InfuseEffect.NONE.setAction(new NoneAction());
        InfuseEffect.STRENGTH.setAction(new StrengthAction());
        InfuseEffect.HEART.setAction(new HeartAction());
        InfuseEffect.HASTE.setAction(new HasteAction());
        InfuseEffect.INVIS.setAction(new InvisAction());
        InfuseEffect.FEATHER.setAction(new FeatherAction());
        InfuseEffect.FROST.setAction(new FrostAction());
        InfuseEffect.THUNDER.setAction(new ThunderAction());
        InfuseEffect.REGEN.setAction(new RegenAction());
        InfuseEffect.OCEAN.setAction(new OceanAction());
        InfuseEffect.FIRE.setAction(new FireAction());
        InfuseEffect.EMERALD.setAction(new EmeraldAction());
        InfuseEffect.SPEED.setAction(new SpeedAction());
        InfuseEffect.ENDER.setAction(new EnderAction());
        for(InfuseEffect effect : effectList){
            effect.setSparkDuration(CooldownFileManager.getEffectDuration(effect));
            effect.setSparkCooldown(CooldownFileManager.getEffectCooldown(effect));
            Bukkit.getLogger().info("Loading effect " + effect.getName() + " With Dur/Col " + CooldownFileManager.getEffectDuration(effect) + " " + CooldownFileManager.getEffectCooldown(effect));
        }
    }

    public static ArrayList<InfuseEffect> getEffectList(){
        return effectList;
    }
}
