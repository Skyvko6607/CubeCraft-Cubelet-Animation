package me.sky.cubelets.cubelet.animation.summer;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.summer.list.*;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SummerCubeletAnimation extends CubeletAnimation {
    public SummerCubeletAnimation(Cubelet cubelet, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(cubelet, player, cubeletLocation, plugin);
        addAnimation(new ArcParticle(this, player, cubeletLocation, plugin));
        addAnimation(new PresentSpawn(this, player, cubeletLocation, plugin));
        addAnimation(new ChestAnimation(this, player, cubeletLocation, plugin));
        addAnimation(new SnakeSpawnAnimation(this, player, cubeletLocation, plugin));
        addAnimation(new FireworkParticle(this, player, cubeletLocation, plugin));
    }

    @Override
    public void finish() {
        getCubeletLocation().getLocation().getBlock().setType(Material.END_PORTAL_FRAME);
        getCubelet().finish(getPlayer(), getCubeletLocation());
    }
}
