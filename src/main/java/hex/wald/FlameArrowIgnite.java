package hex.wald;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fire;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public final class FlameArrowIgnite extends JavaPlugin implements Listener {

    private static final double DEFAULT_CHANCE = 0.25;
    private double chance;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadSettings();
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        loadSettings();
    }

    private void loadSettings() {
        double configured = getConfig().getDouble("chance", DEFAULT_CHANCE);
        if (!Double.isFinite(configured)) {
            getLogger().warning("Invalid ignition chance. Using " + DEFAULT_CHANCE);
            configured = DEFAULT_CHANCE;
        }
        chance = Math.clamp(configured, 0.0, 1.0);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow arrow)) return;
        if (arrow.getFireTicks() <= 0) return;

        Block hit = event.getHitBlock();
        BlockFace face = event.getHitBlockFace();
        if (hit == null || face == null) return;
        if (ThreadLocalRandom.current().nextDouble() >= chance) return;

        Block target = hit.getRelative(face);
        if (!target.getType().isAir()) return;

        Fire fire = (Fire) getServer().createBlockData(Material.FIRE);
        if (face != BlockFace.UP && hit.getType().isFlammable()) {
            fire.setFace(face.getOppositeFace(), true);
        }
        if (!target.canPlace(fire)) return;

        BlockIgniteEvent ignite = new BlockIgniteEvent(target, BlockIgniteEvent.IgniteCause.ARROW, arrow);
        getServer().getPluginManager().callEvent(ignite);
        if (!ignite.isCancelled() && target.getType().isAir() && target.canPlace(fire)) {
            target.setBlockData(fire);
        }
    }
}
