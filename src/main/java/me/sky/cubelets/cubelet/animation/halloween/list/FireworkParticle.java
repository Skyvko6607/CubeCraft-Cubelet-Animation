package me.sky.cubelets.cubelet.animation.halloween.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EvokerFangs;
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
        getCubeletLocation().getLocation().getWorld().spawn(getCubeletLocation().getLocation(), EvokerFangs.class).addScoreboardTag("CubeletEntity");
        MinecraftUtils.scheduleLater(this::finish, 10, getPlugin());
    }

    @Override
    public void update() {

    }

    @Override
    public void remove() {

    }

    @Override
    public void finish() {
        super.finish();
        spawnBigFirework(getCubeletLocation().getLocation().clone().add(0, 1, 0));
        spawnSmallFirework(getStatueLocation(getCubeletLocation().getLocation(), 1, 1));
        spawnSmallFirework(getStatueLocation(getCubeletLocation().getLocation(), -1, 1));
        spawnSmallFirework(getStatueLocation(getCubeletLocation().getLocation(), -1, -1));
        spawnSmallFirework(getStatueLocation(getCubeletLocation().getLocation(), 1, -1));
    }

    private Location getStatueLocation(Location center, int x, int z) {
        return center.clone().add(x * 2, 1, z * 2);
    }

    private void spawnBigFirework(Location loc) {
        FireworkEffect effect = FireworkEffect.builder().withColor(Color.AQUA, Color.WHITE).withFade(Color.RED).with(FireworkEffect.Type.STAR).build();
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(0);
        meta.addEffect(effect);
        firework.setFireworkMeta(meta);
        firework.detonate();
        firework.addScoreboardTag("CubeletEntity");
    }

    private void spawnSmallFirework(Location loc) {
        FireworkEffect effect = FireworkEffect.builder().withColor(Color.RED, Color.YELLOW).withFade(Color.RED).with(FireworkEffect.Type.BALL).build();
        Firework firework = loc.getWorld().spawn(loc, Firework.class);
        FireworkMeta meta = firework.getFireworkMeta();
        meta.setPower(0);
        meta.addEffect(effect);
        firework.setFireworkMeta(meta);
        firework.detonate();
        firework.addScoreboardTag("CubeletEntity");
    }

    @Override
    public int getUpdateTime() {
        return 0;
    }
}
