package me.sky.cubelets.cubelet.animation.summer.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.particle.ParticleEffect;
import me.sky.cubelets.utils.particle.data.color.RegularColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ArcParticle extends CubeletAnimationPart {
    private final List<Location> particles = new ArrayList<>();
    private int angle = 0;

    public ArcParticle(CubeletAnimation cubeletAnimation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(cubeletAnimation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void update() {
        particles.forEach(location -> ParticleEffect.REDSTONE.display(location, new RegularColor(255, 0, 0), Bukkit.getOnlinePlayers()));
        if (angle >= 90) {
            finish();
            return;
        }
        particles.add(getParticleLocation(1, 1));
        particles.add(getParticleLocation(-1, 1));
        particles.add(getParticleLocation(1, -1));
        particles.add(getParticleLocation(-1, -1));
        angle += 4;
    }

    @Override
    public void remove() {

    }

    @Override
    public int getUpdateTime() {
        return 1;
    }

    private Location getParticleLocation(int x, int z) {
        final Location loc = getCubeletLocation().getLocation().clone().add(0, 1, 0);
        double size = 2.5;
        loc.setX(loc.getX() + x * (Math.cos(Math.toRadians(angle)) * size));
        loc.setZ(loc.getZ() + z * (Math.cos(Math.toRadians(angle)) * size));
        loc.setY(loc.getY() + (Math.sin(Math.toRadians(angle)) * size));
        return loc;
    }
}
