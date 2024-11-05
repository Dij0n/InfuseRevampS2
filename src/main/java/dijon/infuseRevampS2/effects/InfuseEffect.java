package dijon.infuseRevampS2.effects;

import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;

public enum InfuseEffect {

    NONE("none", "\uE001", "\uE001", TextColor.color(0x0)),
    STRENGTH("strength", "\uE002", "\uE014", TextColor.color(0xE0191D)),
    HEART("heart", "\uE003", "\uE015", TextColor.color(0xA61719)),
    HASTE("haste", "\uE004", "\uE016", TextColor.color(0xFFDC1E)),
    INVIS("invis", "\uE005", "\uE017", TextColor.color(0xB895FF)),
    FEATHER("feather", "\uE006", "\uE018", TextColor.color(0xAE60D)),
    FROST("frost", "\uE007", "\uE019", TextColor.color(0x3FE4FF)),
    THUNDER("thunder", "\uE008", "\uE020", TextColor.color(0xE5E349)),
    REGEN("regen", "\uE009", "\uE021", TextColor.color(0xFFB6AC)),
    OCEAN("ocean", "\uE010", "\uE022", TextColor.color(0x137BFF)),
    FIRE("fire", "\uE011", "\uE023", TextColor.color(0xFF5103)),
    EMERALD("emerald", "\uE012", "\uE024", TextColor.color(0x1EFF12)),
    SPEED("speed", "\uE013", "\uE025", TextColor.color(0x5EE1FF)),
    ENDER("ender", "\uE026", "\uE027", TextColor.color(0x69248E));

    final String name;
    final String texture;
    final String textureSpark;
    final TextColor color;
    int sparkDuration = 0;
    int sparkCooldown = 0;

    final static ArrayList<InfuseEffect> effectList = new ArrayList<>();

    InfuseEffect(String name, String texture, String textureSpark, TextColor color){
        this.name = name;
        this.texture = texture;
        this.textureSpark = textureSpark;
        this.color = color;

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
    public String getTextureSpark() {
        return textureSpark;
    }
    public int getSparkDuration() {
        return sparkDuration;
    }
    public int getSparkCooldown() {
        return sparkCooldown;
    }

    public static void initializeEffects(){
        effectList.add(InfuseEffect.NONE);
        effectList.add(InfuseEffect.STRENGTH);
        effectList.add(InfuseEffect.HEART);
        effectList.add(InfuseEffect.HASTE);
        effectList.add(InfuseEffect.INVIS);
        effectList.add(InfuseEffect.FEATHER);
        effectList.add(InfuseEffect.FROST);
        effectList.add(InfuseEffect.REGEN);
        effectList.add(InfuseEffect.OCEAN);
        effectList.add(InfuseEffect.FIRE);
        effectList.add(InfuseEffect.EMERALD);
        effectList.add(InfuseEffect.SPEED);
        effectList.add(InfuseEffect.ENDER);
    }
    public static ArrayList<InfuseEffect> getEffectList(){
        return effectList;
    }
}
