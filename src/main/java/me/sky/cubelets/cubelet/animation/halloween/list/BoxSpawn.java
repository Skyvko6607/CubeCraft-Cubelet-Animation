package me.sky.cubelets.cubelet.animation.halloween.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import me.sky.cubelets.utils.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

public class BoxSpawn extends CubeletAnimationPart {
    private ArmorStand box;
    private double y = 0;
    private int angle = 0;
    private int ringAngle = 0;
    private Location start;

    public BoxSpawn(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
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
//        ParticleEffect.FLAME.display(getCubeletLocation().getLocation(), 1, 0, 1, 0, 3, null, Bukkit.getOnlinePlayers());
        if (box == null || start == null) {
            return;
        }
        ringAngle = ringAngle > 360 ? 0 : ringAngle + 15;
        ParticleEffect.CRIT.display(box.getLocation().clone().add(
                Math.cos(Math.toRadians(ringAngle)) / 2,
                (Math.sin(Math.toRadians(ringAngle)) / 2) + box.getHeight(),
                Math.sin(Math.toRadians(ringAngle)) / 2),
                Bukkit.getOnlinePlayers());
        ParticleEffect.END_ROD.display(box.getLocation().clone().add(0, box.getHeight() / 1.5, 0), Bukkit.getOnlinePlayers());
        if (y >= 1) {
            if (!isFinished()) {
                playBreakingAnimation();
            }
            finish();
            return;
        }
        box.setRotation(angle, 0);
        angle += 30;
        Location loc = start.clone().add(0, y, 0);
        loc.setYaw(box.getLocation().getYaw());
        box.teleport(loc);
        y += 0.025;
        loc.getWorld().playSound(loc, Sound.BLOCK_NOTE_BLOCK_BIT, 0.5f, (float) (y * 1.75 + 0.25));
    }

    private void playBreakingAnimation() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getCubeletAnimation().getAnimationByClass(EvokerAnimation.class).isOpened() || getCubeletAnimation().isFinished()) {
                    this.cancel();
                    return;
                }
                Location loc = MinecraftUtils.getLookAtLocation(box, getPlayer().getEyeLocation());
                double x = getPlugin().getRandom().nextInt(60) - 30;
                double z = getPlugin().getRandom().nextInt(60) - 30;
                box.setHeadPose(new EulerAngle(Math.toRadians(x), Math.toRadians(loc.getYaw()), Math.toRadians(z)));
                box.getWorld().playSound(box.getLocation(), Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.25f, (float) Math.random() + 0.5f);
            }
        }.runTaskTimer(getPlugin(), 0, 20);
    }

    @Override
    public void remove() {
        box.remove();
    }

    @Override
    public int getUpdateTime() {
        return 2;
    }

    public ArmorStand getBox() {
        return box;
    }
}
