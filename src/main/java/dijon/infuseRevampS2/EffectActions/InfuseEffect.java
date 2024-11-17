package dijon.infuseRevampS2.EffectActions;

import dijon.infuseRevampS2.Data.CooldownFileManager;
import dijon.infuseRevampS2.EffectActions.Actions.*;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import net.kyori.adventure.text.format.TextColor;

import java.util.ArrayList;

public enum InfuseEffect {

    NONE("none", "\uE001", "\uE001", 0x0, new NoneAction()),
    STRENGTH("strength", "\uE002", "\uE014", 0xE0191D, new StrengthAction()),
    HEART("heart", "\uE003", "\uE015", 0xA61719, new HeartAction()),
    HASTE("haste", "\uE004", "\uE016", 0xFFDC1E, new HasteAction()),
    INVIS("invis", "\uE005", "\uE017", 0xB895FF, new InvisAction()),
    FEATHER("feather", "\uE006", "\uE018", 0xAE60D, new FeatherAction()),
    FROST("frost", "\uE007", "\uE019", 0x3FE4FF, new FrostAction()),
    THUNDER("thunder", "\uE008", "\uE020", 0xE5E349, new ThunderAction()),
    REGEN("regen", "\uE009", "\uE021", 0xFFB6AC, new RegenAction()),
    OCEAN("ocean", "\uE010", "\uE022", 0x137BFF, new OceanAction()),
    FIRE("fire", "\uE011", "\uE023", 0xFF5103, new FireAction()),
    EMERALD("emerald", "\uE012", "\uE024", 0x1EFF12, new EmeraldAction()),
    SPEED("speed", "\uE013", "\uE025", 0x5EE1FF, new SpeedAction()),
    ENDER("ender", "\uE026", "\uE027", 0x69248E, new EnderAction());

    final String name;
    final String texture;
    final String textureSpark;
    final TextColor color;
    final int rawColor;
    final InfuseAction action;
    int sparkDuration = 0;
    int sparkCooldown = 0;

    final static ArrayList<InfuseEffect> effectList = new ArrayList<>();

    InfuseEffect(String name, String texture, String textureSpark, int rawColor, InfuseAction action){
        this.name = name;
        this.texture = texture;
        this.textureSpark = textureSpark;
        this.color = TextColor.color(rawColor);
        this.rawColor = rawColor;
        this.action = action;

        //Some method here to get the cooldowns from storage
        //Some method here to get the cooldowns from storage
        //Some method here to get the cooldowns from storage
        this.sparkDuration = 0;
        this.sparkCooldown = 0;
        //Some method here to get the cooldowns from storage
        //Some method here to get the cooldowns from storage
        //Some method here to get the cooldowns from storage
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

    public static void initializeEffects(){
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

    public static void loadTimesIntoEffects(){
        for(InfuseEffect effect : effectList){
            effect.setSparkDuration(CooldownFileManager.getEffectDuration(effect));
            effect.setSparkCooldown(CooldownFileManager.getEffectCooldown(effect));
        }
    }

    public static ArrayList<InfuseEffect> getEffectList(){
        return effectList;
    }
}
