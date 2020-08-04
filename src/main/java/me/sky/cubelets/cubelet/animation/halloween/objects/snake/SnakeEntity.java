package me.sky.cubelets.cubelet.animation.halloween.objects.snake;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.utils.Laser;
import me.sky.cubelets.utils.MinecraftUtils;
import me.sky.cubelets.utils.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class SnakeEntity {
    private final ICubeletsPlugin plugin;
    private final ArmorStand[] armorStands = new ArmorStand[16];
    private Laser laser;

    public SnakeEntity(Cubelet cubelet, Location loc, ICubeletsPlugin plugin) {
        this.plugin = plugin;
        loc.setPitch(-90);
        armorStands[0] = getHeadArmorStand(loc);
        for (int i = 1; i < armorStands.length; i++) {
            armorStands[i] = getBodyArmorStand(loc);
        }
        armorStands[0].teleport(loc.subtract(loc.getDirection()));
        for (int i = 0; i < armorStands.length; i++) {
            if (i == 0) {
                continue;
            }
            armorStands[i].teleport(armorStands[i - 1].getLocation().subtract(armorStands[i - 1].getLocation().getDirection()));
        }
    }

    public void spawn(ArmorStand box, Vector[] path, ISnakeComplete snakeComplete) {
        try {
            laser = new Laser(armorStands[0].getLocation().add(0, armorStands[0].getHeight(), 0),
                    box.getLocation().add(0, box.getHeight(), 0), -1, 30);
            laser.start(plugin);
        } catch (ReflectiveOperationException e) {
        }

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (!armorStands[0].isValid()) {
                    this.cancel();
                    laser.stop();
                    return;
                }
                if (armorStands[0].getLocation().distance(path[index].toLocation(armorStands[0].getWorld())) <= 0.25f) {
                    index++;
                }
                if (index >= path.length) {
                    for (ArmorStand armorStand : armorStands) {
                        armorStand.remove();
                    }
                    this.cancel();
                    laser.stop();
                    snakeComplete.complete();
                    return;
                }
                Location standLoc = armorStands[0].getLocation().clone().add(0, armorStands[0].getHeight(), 0);
                if (box.isValid()) {
                    try {
                        laser.callColorChange();
                        laser.moveStart(standLoc);
                    } catch (ReflectiveOperationException e) {
                    }
                }
                for (int i = 0; i < armorStands.length; i++) {
                    ArmorStand armorStand = armorStands[i];
                    if (i == 0) {
                        MinecraftUtils.lookAtLocation(armorStand, path[index].toLocation(armorStands[0].getWorld()));
                        armorStand.teleport(armorStand.getLocation().add(armorStand.getLocation().getDirection().multiply(0.25)));
                        continue;
                    }
                    if (i % 2 == 0) {
                        ParticleEffect.SMOKE_LARGE.display(armorStand.getLocation().clone().add(0, armorStand.getHeight() / 2, 0),
                                0.15f, 0.15f, 0.15f, 0, 1, null, Bukkit.getOnlinePlayers());
                    }
                    if (armorStand.getLocation().distance(armorStands[i - 1].getLocation()) <= 0.35f) {
                        continue;
                    }
                    MinecraftUtils.lookAtLocation(armorStand, armorStands[i - 1].getLocation());
                    armorStand.teleport(armorStand.getLocation().add(armorStand.getLocation().getDirection().multiply(0.25)));
                }
            }
        }.runTaskTimer(plugin, 0, 1);
    }

    private ArmorStand getHeadArmorStand(Location location) {
        ArmorStand armorStand = getBodyArmorStand(location);
        armorStand.getEquipment().setHelmet(new ItemStack(Material.DRAGON_HEAD));
        return armorStand;
    }

    private ArmorStand getBodyArmorStand(Location location) {
        ArmorStand armorStand = location.getWorld().spawn(location, ArmorStand.class, stand -> {
            stand.setSilent(true);
            stand.setVisible(false);
            stand.setGravity(false);
        });
        armorStand.setSmall(true);
        armorStand.setBasePlate(false);
        armorStand.getEquipment().setHelmet(new ItemStack(Material.BLACK_CONCRETE));
        armorStand.addScoreboardTag("CubeletEntity");
        return armorStand;
    }

    private float toDegree(double angle) {
        return (float) Math.toDegrees(angle);
    }

    private Vector getVector(Entity entity) {
        return entity.getLocation().toVector();
    }
}
