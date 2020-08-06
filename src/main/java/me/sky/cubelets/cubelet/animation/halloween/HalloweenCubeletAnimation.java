package me.sky.cubelets.cubelet.animation.halloween;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.halloween.list.*;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.location.CubeletLocation;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class HalloweenCubeletAnimation extends CubeletAnimation {
    public HalloweenCubeletAnimation(Cubelet cubelet, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(cubelet, player, cubeletLocation, plugin);
        addAnimation(new ArcParticle(this, player, cubeletLocation, plugin));
        addAnimation(new ChainsEffect(this, player, cubeletLocation, plugin));
        addAnimation(new BoxSpawn(this, player, cubeletLocation, plugin));
        addAnimation(new EvokerAnimation(this, player, cubeletLocation, plugin));
        addAnimation(new SnakeSpawnAnimation(this, player, cubeletLocation, plugin));
        addAnimation(new FireworkParticle(this, player, cubeletLocation, plugin));
    }

    @Override
    public void finish() {
        getCubeletLocation().getLocation().getBlock().setType(Material.END_PORTAL_FRAME);
        getCubelet().finish(getPlayer(), getCubeletLocation());
    }
}
