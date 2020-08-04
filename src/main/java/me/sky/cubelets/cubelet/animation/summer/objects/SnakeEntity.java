package me.sky.cubelets.cubelet.animation.summer.objects;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.objects.Cubelet;
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

    public void spawn(Vector[] path, ISnakeComplete snakeComplete) {
        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (!armorStands[0].isValid()) {
                    this.cancel();
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
                    snakeComplete.complete();
                    return;
                }
                for (int i = 0; i < armorStands.length; i++) {
                    ArmorStand armorStand = armorStands[i];
                    ParticleEffect.FLAME.display(armorStand.getLocation(), 0.15f, 0.15f, 0.15f, 0, 2, null, Bukkit.getOnlinePlayers());
                    if (i == 0) {
                        lookAtLocation(armorStand, path[index].toLocation(armorStands[0].getWorld()));
                        armorStand.teleport(armorStand.getLocation().add(armorStand.getLocation().getDirection().multiply(0.25)));
                        continue;
                    }
                    if (armorStand.getLocation().distance(armorStands[i - 1].getLocation()) <= 0.35f) {
                        continue;
                    }
                    lookAtEntity(armorStand, armorStands[i - 1]);
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

    private void lookAtLocation(Entity source, Location target) {
        Vector direction = getVector(source).subtract(target.toVector()).normalize();
        double x = direction.getX();
        double y = direction.getY();
        double z = direction.getZ();

        Location changed = source.getLocation().clone();
        changed.setYaw(180 - toDegree(Math.atan2(x, z)));
        changed.setPitch(90 - toDegree(Math.acos(y)));
        source.setRotation(changed.getYaw(), changed.getPitch());
        ((ArmorStand) source).setHeadPose(new EulerAngle(Math.toRadians(changed.getPitch()), ((ArmorStand) source).getHeadPose().getY(), 0));
    }

    private void lookAtEntity(Entity source, Entity target) {
        lookAtLocation(source, target.getLocation());
    }

    private float toDegree(double angle) {
        return (float) Math.toDegrees(angle);
    }

    private Vector getVector(Entity entity) {
        return entity.getLocation().toVector();
    }
}
