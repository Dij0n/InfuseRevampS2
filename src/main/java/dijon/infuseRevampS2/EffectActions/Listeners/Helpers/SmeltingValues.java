package dijon.infuseRevampS2.EffectActions.Listeners.Helpers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class SmeltingValues {

    public static final Map<Material, Material > smeltMap = new HashMap<>();

    public static void initialize(){
        smeltMap.put(Material.RAW_IRON, Material.IRON_INGOT);
        smeltMap.put(Material.RAW_GOLD, Material.GOLD_INGOT);
        smeltMap.put(Material.RAW_COPPER, Material.COPPER_INGOT);
        smeltMap.put(Material.NETHER_GOLD_ORE, Material.GOLD_INGOT);
        smeltMap.put(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP);

        smeltMap.put(Material.COBBLESTONE, Material.STONE);
        smeltMap.put(Material.STONE, Material.SMOOTH_STONE);
        smeltMap.put(Material.STONE_BRICKS, Material.CRACKED_STONE_BRICKS);
        smeltMap.put(Material.COBBLED_DEEPSLATE, Material.DEEPSLATE);
        smeltMap.put(Material.DEEPSLATE_BRICKS, Material.CRACKED_DEEPSLATE_BRICKS);
        smeltMap.put(Material.SANDSTONE, Material.SMOOTH_SANDSTONE);
        smeltMap.put(Material.RED_SANDSTONE, Material.SMOOTH_RED_SANDSTONE);
        smeltMap.put(Material.NETHER_BRICKS, Material.CRACKED_NETHER_BRICKS);
        smeltMap.put(Material.BASALT, Material.SMOOTH_BASALT);
        smeltMap.put(Material.POLISHED_BLACKSTONE_BRICKS, Material.CRACKED_POLISHED_BLACKSTONE_BRICKS);
        smeltMap.put(Material.QUARTZ_BLOCK, Material.SMOOTH_QUARTZ);

        smeltMap.put(Material.CLAY, Material.TERRACOTTA);
        smeltMap.put(Material.BLACK_TERRACOTTA, Material.BLACK_GLAZED_TERRACOTTA);
        smeltMap.put(Material.BLUE_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA);
        smeltMap.put(Material.BROWN_TERRACOTTA, Material.BROWN_GLAZED_TERRACOTTA);
        smeltMap.put(Material.CYAN_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA);
        smeltMap.put(Material.GRAY_TERRACOTTA, Material.GRAY_GLAZED_TERRACOTTA);
        smeltMap.put(Material.GREEN_TERRACOTTA, Material.GREEN_GLAZED_TERRACOTTA);
        smeltMap.put(Material.LIGHT_BLUE_TERRACOTTA, Material.LIGHT_BLUE_GLAZED_TERRACOTTA);
        smeltMap.put(Material.LIGHT_GRAY_TERRACOTTA, Material.LIGHT_GRAY_GLAZED_TERRACOTTA);
        smeltMap.put(Material.LIME_TERRACOTTA, Material.LIME_GLAZED_TERRACOTTA);
        smeltMap.put(Material.MAGENTA_TERRACOTTA, Material.MAGENTA_GLAZED_TERRACOTTA);
        smeltMap.put(Material.ORANGE_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA);
        smeltMap.put(Material.PINK_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA);
        smeltMap.put(Material.PURPLE_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA);
        smeltMap.put(Material.RED_TERRACOTTA, Material.RED_GLAZED_TERRACOTTA);
        smeltMap.put(Material.WHITE_TERRACOTTA, Material.WHITE_GLAZED_TERRACOTTA);
        smeltMap.put(Material.YELLOW_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA);

        smeltMap.put(Material.SAND, Material.GLASS);
        smeltMap.put(Material.RED_SAND, Material.GLASS);

        smeltMap.put(Material.WET_SPONGE, Material.SPONGE);

        smeltMap.put(Material.OAK_LOG, Material.CHARCOAL);
        smeltMap.put(Material.ACACIA_LOG, Material.CHARCOAL);
        smeltMap.put(Material.BIRCH_LOG, Material.CHARCOAL);
        smeltMap.put(Material.CHERRY_LOG, Material.CHARCOAL);
        smeltMap.put(Material.JUNGLE_LOG, Material.CHARCOAL);
        smeltMap.put(Material.DARK_OAK_LOG, Material.CHARCOAL);
        smeltMap.put(Material.MANGROVE_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_OAK_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_ACACIA_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_BIRCH_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_CHERRY_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_JUNGLE_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_DARK_OAK_LOG, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_MANGROVE_LOG, Material.CHARCOAL);
        smeltMap.put(Material.OAK_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.ACACIA_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.BIRCH_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.CHERRY_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.JUNGLE_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.DARK_OAK_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.MANGROVE_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_OAK_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_ACACIA_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_BIRCH_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_CHERRY_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_JUNGLE_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_DARK_OAK_WOOD, Material.CHARCOAL);
        smeltMap.put(Material.STRIPPED_MANGROVE_WOOD, Material.CHARCOAL);

        smeltMap.put(Material.SEA_PICKLE, Material.LIME_DYE);
        smeltMap.put(Material.CACTUS, Material.GREEN_DYE);
        smeltMap.put(Material.NETHERRACK, Material.NETHER_BRICK);
        smeltMap.put(Material.KELP, Material.DRIED_KELP);

    }

    public static Material getSmelted(Material material) {
        return smeltMap.get(material);
    }

    public static boolean isSmeltable(Material material){
        return smeltMap.containsKey(material);
    }
}

