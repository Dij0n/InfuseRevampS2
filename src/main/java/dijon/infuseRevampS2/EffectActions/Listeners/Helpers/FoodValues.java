package dijon.infuseRevampS2.EffectActions.Listeners.Helpers;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class FoodValues {

    public static final Map<Material, FoodData> foodMap = new HashMap<>();

    static {
        // Common Foods
        foodMap.put(Material.APPLE, new FoodData(4, 2.4f));
        foodMap.put(Material.BAKED_POTATO, new FoodData(5, 6.0f));
        foodMap.put(Material.BREAD, new FoodData(5, 6.0f));
        foodMap.put(Material.COOKED_BEEF, new FoodData(8, 12.8f));
        foodMap.put(Material.COOKED_CHICKEN, new FoodData(6, 7.2f));
        foodMap.put(Material.COOKED_MUTTON, new FoodData(6, 9.6f));
        foodMap.put(Material.COOKED_PORKCHOP, new FoodData(8, 12.8f));
        foodMap.put(Material.COOKED_SALMON, new FoodData(6, 9.6f));
        foodMap.put(Material.CARROT, new FoodData(3, 3.6f));
        foodMap.put(Material.GOLDEN_CARROT, new FoodData(6, 14.4f));
        foodMap.put(Material.MELON_SLICE, new FoodData(2, 1.2f));
        foodMap.put(Material.BEEF, new FoodData(3, 1.8f));
        foodMap.put(Material.CHICKEN, new FoodData(2, 1.2f));
        foodMap.put(Material.MUTTON, new FoodData(2, 1.2f));
        foodMap.put(Material.PORKCHOP, new FoodData(3, 1.8f));
        foodMap.put(Material.RABBIT, new FoodData(3, 1.8f));
        foodMap.put(Material.SALMON, new FoodData(2, 1.2f));
        foodMap.put(Material.COOKED_COD, new FoodData(5, 6.0f));
        foodMap.put(Material.COD, new FoodData(2, 0.4f));
        foodMap.put(Material.POTATO, new FoodData(1, 0.6f));
        foodMap.put(Material.PUMPKIN_PIE, new FoodData(8, 4.8f));
        foodMap.put(Material.ROTTEN_FLESH, new FoodData(4, 0.8f));
        foodMap.put(Material.SPIDER_EYE, new FoodData(2, 3.2f));

        // Special Foods
        foodMap.put(Material.ENCHANTED_GOLDEN_APPLE, new FoodData(4, 9.6f));
        foodMap.put(Material.GOLDEN_APPLE, new FoodData(4, 9.6f));

        // Miscellaneous
        foodMap.put(Material.COOKIE, new FoodData(2, 0.4f));
        foodMap.put(Material.SWEET_BERRIES, new FoodData(2, 0.4f));
        foodMap.put(Material.DRIED_KELP, new FoodData(1, 0.6f));
        foodMap.put(Material.HONEY_BOTTLE, new FoodData(6, 1.2f));
    }

    public static FoodData getFoodData(Material material) {
        return foodMap.getOrDefault(material, new FoodData(0, 0.0f));
    }

    public record FoodData(int hunger, float saturation) {
    }
}

