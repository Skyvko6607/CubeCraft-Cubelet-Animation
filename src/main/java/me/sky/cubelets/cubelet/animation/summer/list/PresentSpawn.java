package me.sky.cubelets.cubelet.animation.summer.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class PresentSpawn extends CubeletAnimationPart {
    private ArmorStand box;
    private double y = 0;
    private int angle = 0;
    private Location start;

    public PresentSpawn(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(animation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
        box = getCubeletLocation().getLocation().getWorld().spawn(getCubeletLocation().getLocation().clone().subtract(0, 1, 0), ArmorStand.class, stand -> {
            stand.setSilent(true);
            stand.setVisible(false);
            stand.setGravity(false);
        });
        box.setInvulnerable(true);
        box.setSmall(true);
        box.addScoreboardTag("CubeletEntity");
        box.getEquipment().setHelmet(getCubeletAnimation().getCubelet().getHeadItem());
        box.setBasePlate(false);
        start = getCubeletLocation().getLocation().clone().subtract(0, box.getHeight() / 2, 0);
        start.setYaw(0);
        start.setPitch(0);
    }

    @Override
    public void update() {
        if (box == null || start == null) {
            return;
        }
        box.setRotation(angle, 0);
        angle += 30;
        if (y >= 1) {
            finish();
            return;
        }
        Location loc = start.clone().add(0, y, 0);
        loc.setYaw(box.getLocation().getYaw());
        box.teleport(loc);
        y += 0.025;
    }

    @Override
    public void remove() {
        box.remove();
    }

    @Override
    public int getUpdateTime() {
        return 2;
    }
}
