package hex.wald;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Fire;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public final class FlameArrowIgnite extends JavaPlugin implements Listener {

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
        FileConfiguration c = getConfig();
        chance = clamp01(c.getDouble("chance", 0.25));
    }

    private static double clamp01(double v) {
        return v < 0 ? 0 : (v > 1 ? 1 : v);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        if (!(e.getEntity() instanceof Arrow arrow)) return;
        if (arrow.getFireTicks() <= 0) return;

        Block hit = e.getHitBlock();
        BlockFace face = e.getHitBlockFace();
        if (hit == null || face == null) return;

        if (ThreadLocalRandom.current().nextDouble() >= chance) return;

        Block target = (face == BlockFace.UP) ? hit.getRelative(BlockFace.UP) : hit.getRelative(face);
        if (!target.getType().isAir()) return;

        target.setType(Material.FIRE, true);

        Fire data = (Fire) target.getBlockData();
        if (face == BlockFace.DOWN) data.setFace(BlockFace.UP, true);
        else if (face != BlockFace.UP) data.setFace(face.getOppositeFace(), true);
        target.setBlockData(data, true);
    }
}