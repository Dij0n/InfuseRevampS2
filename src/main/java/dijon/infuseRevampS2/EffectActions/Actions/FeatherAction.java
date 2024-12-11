package dijon.infuseRevampS2.EffectActions.Actions;

import dijon.infuseRevampS2.Data.PlayerDataManager;
import dijon.infuseRevampS2.EffectActions.InfuseEffect;
import dijon.infuseRevampS2.EffectActions.Listeners.FeatherListener;
import dijon.infuseRevampS2.EffectActions.Listeners.Helpers.Helpers;
import dijon.infuseRevampS2.EffectActions.Templates.InfuseAction;
import dijon.infuseRevampS2.InfuseRevampS2;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class FeatherAction extends InfuseAction {

    public FeatherAction(){
        super(InfuseEffect.FEATHER);
    }

    @Override
    protected void onEquip(Player player) {

    }

    @Override
    protected void onUnequipped(Player player) {

    }

    @Override
    protected void onSparked(Player player) {
        player.setVelocity(new Vector(0, 1.06, 0));
        Bukkit.getScheduler().runTaskLater(InfuseRevampS2.instance, ()->{
            FeatherListener.featherPoundMap.put(player.getUniqueId(), true);
        }, 5);
    }

    @Override
    protected void onSparkEnd(Player player) {
        FeatherListener.featherPoundMap.put(player.getUniqueId(), false);
    }

    @Override
    protected BukkitRunnable createStandardInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {

            }
        };
    }

    @Override
    protected BukkitRunnable createSparkedInterim(Player player) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                if(!FeatherListener.featherPoundMap.getOrDefault(player.getUniqueId(), false)) return;
                Block blockUnder = player.getWorld().getBlockAt(player.getLocation().add(new Vector(0, -1, 0)));

                if(!blockUnder.getType().equals(Material.AIR)){
                    FeatherListener.featherPoundMap.put(player.getUniqueId(), false);

                    player.setVelocity(new Vector(0, 3, 0));
                    Vector v = player.getLocation().getDirection().multiply(5);
                    if(v.getY() < 1) v.setY(1);
                    player.setVelocity(v);

                    player.getWorld().playSound(player.getLocation(), Sound.ITEM_MACE_SMASH_GROUND_HEAVY, 1F, 1F);
                    groundPound(player);
                }
            }
        };
    }

    private void groundPound(Player player){
        featherPoundParticles(player);
        for(Entity e : player.getNearbyEntities(5, 3, 5)){
            if(!(e instanceof LivingEntity livingEntity)) continue;
            if(e instanceof Villager) continue;
            if(PlayerDataManager.getTrustedList(player.getUniqueId()).contains(livingEntity.getUniqueId())) continue;

            Helpers.trueDamage(livingEntity, 6);
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 100, 0));
            livingEntity.setVelocity(new Vector(0, 1, 0));
        }
    }

    public static void featherPoundParticles (Player p){
        World world = p.getWorld();
        double x;
        double z;
        double theta = 0;
        int radius = 5;

        while(theta <= Math.PI * 4){
            x = Math.sin(theta) * radius;
            z = Math.cos(theta) * radius;
            int randomVal = (int) (Math.floor(Math.random() * 40) + 30);
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(randomVal, randomVal, randomVal), 2);
            Location circleLoc = new Location(world, p.getLocation().getX() + x, p.getLocation().getY(), p.getLocation().getZ() + z);
            world.spawnParticle(Particle.POOF, circleLoc, 3);
            theta += 0.5D;
        }

        world.spawnParticle(Particle.POOF, p.getLocation(), 3);

    }
}
