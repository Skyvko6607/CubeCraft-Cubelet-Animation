package me.sky.cubelets.cubelet.animation.summer.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkParticle extends CubeletAnimationPart {
    public FireworkParticle(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(animation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
        FireworkEffect effect = FireworkEffect.builder().withColor(Color.AQUA, Color.WHITE).withFade(Color.RED).with(FireworkEffect.Type.STAR).build();
        Location loc = getCubeletLocation().getLocation().clone().add(0, 1, 0);
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(0);
        meta.addEffect(effect);
        firework.setFireworkMeta(meta);
        firework.detonate();
        MinecraftUtils.scheduleLater(this::finish, 10, getPlugin());
    }

    @Override
    public void update() {

    }

    @Override
    public void remove() {

    }

    @Override
    public int getUpdateTime() {
        return 0;
    }
}
