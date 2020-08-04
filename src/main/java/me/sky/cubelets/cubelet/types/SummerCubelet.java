package me.sky.cubelets.cubelet.types;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.cubelet.objects.CubeletRarity;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.particle.ParticleEffect;
import me.sky.cubelets.utils.particle.data.color.RegularColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SummerCubelet extends Cubelet {
    public SummerCubelet(ICubeletsPlugin plugin) {
        super(plugin);
    }

    @Override
    public String getId() {
        return "summer";
    }

    @Override
    public String getHeadTexture() {
        return "5a5ab05ea254c32e3c48f3fdcf9fd9d77d3cba04e6b5ec2e68b3cbdcfac3fd";
    }

    @Override
    public CubeletRarity getRarity() {
        return CubeletRarity.RARE;
    }

    @Override
    public int[] getLootPoints() {
        return new int[]{50, 200};
    }

    @Override
    public long getExpiration() {
        return TimeUnit.DAYS.toMillis(13);
    }

    @Override
    public void start(Player player, CubeletLocation cubeletLocation) {
        super.start(player, cubeletLocation);
        playSinParticle(player, cubeletLocation);
    }

    private void playSinParticle(Player player, CubeletLocation cubeletLocation) {
        new BukkitRunnable() {
            final int size = 2;
            final List<Location> locs = new ArrayList<>();
            int angle = 0;
            boolean blockSpawn = false;

            @Override
            public void run() {
                locs.add(getParticleLocation(1, 1));
                locs.add(getParticleLocation(-1, 1));
                locs.add(getParticleLocation(1, -1));
                locs.add(getParticleLocation(-1, -1));
                locs.forEach(location -> ParticleEffect.REDSTONE.display(location, new RegularColor(0, 252, 194), Bukkit.getOnlinePlayers()));
                if (angle >= 90) {
                    if (!cubeletLocation.isOpening()) {
                        this.cancel();
                    }
                    if (!blockSpawn) {
                        blockSpawn = true;
                        spawnBlock(player, cubeletLocation);
                    }
                    return;
                }
                angle += 4;
            }

            private Location getParticleLocation(int x, int z) {
                final Location loc = cubeletLocation.getLocation().clone().add(0, 1, 0);
                loc.setX(loc.getX() + x * (Math.cos(Math.toRadians(angle)) * size));
                loc.setZ(loc.getZ() + z * (Math.cos(Math.toRadians(angle)) * size));
                loc.setY(loc.getY() + (Math.sin(Math.toRadians(angle)) * size));
                return loc;
            }
        }.runTaskTimer(getPlugin(), 0, 1);
    }

    private void spawnBlock(Player player, CubeletLocation cubeletLocation) {
        ArmorStand armorStand = cubeletLocation.getLocation().getWorld().spawn(cubeletLocation.getLocation().clone().subtract(0, 1, 0), ArmorStand.class);
        armorStand.setInvulnerable(true);
        armorStand.setSmall(true);
        armorStand.setGravity(false);
        armorStand.addScoreboardTag("Cubelet");
        armorStand.getEquipment().setHelmet(getHeadItem());
        armorStand.setVisible(false);
        armorStand.setBasePlate(false);
        final Location start = cubeletLocation.getLocation().clone().subtract(0, armorStand.getHeight() / 2, 0);
        start.setYaw(0);
        start.setPitch(0);
        new BukkitRunnable() {
            double y = 0;
            int angle = 0;
            @Override
            public void run() {
                if (y >= 1) {
                    this.cancel();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            finish(player, cubeletLocation);
                            armorStand.remove();
                        }
                    }.runTaskLater(getPlugin(), 20 * 3);
                    return;
                }
                Location loc = start.clone().add(0, y, 0);
                loc.setYaw(angle);
                armorStand.teleport(loc);
                y += 0.025;
                angle += 27;
            }
        }.runTaskTimer(getPlugin(), 0, 2);
    }
}
