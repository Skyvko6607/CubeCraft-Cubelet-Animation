package me.sky.cubelets.cubelet.animation.halloween.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.cubelet.animation.halloween.objects.snake.SnakeEntity;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SnakeSpawnAnimation extends CubeletAnimationPart {

    public SnakeSpawnAnimation(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(animation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
        getCubeletLocation().getLocation().getWorld().playSound(getCubeletLocation().getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 0.5f, 1);
        Location spawn = getCubeletLocation().getLocation().clone().add(0, 1.35, 0);
        SnakeEntity snakeEntity = new SnakeEntity(getCubeletAnimation().getCubelet(), spawn.clone(), getPlugin());
        SnakeEntity snakeEntity2 = new SnakeEntity(getCubeletAnimation().getCubelet(), spawn.clone(), getPlugin());
        Vector[] path = getSnakePath(0, spawn.clone());
        Vector[] path2 = getSnakePath(180, spawn.clone());
        MinecraftUtils.scheduleLater(() -> getCubeletLocation().getLocation().getWorld().playSound(getCubeletLocation().getLocation(), Sound.ENTITY_VEX_CHARGE, 1, 0), 10, getPlugin());
        snakeEntity.spawn(getCubeletAnimation().getAnimationByClass(BoxSpawn.class).getBox(), path, () -> { });
        snakeEntity2.spawn(getCubeletAnimation().getAnimationByClass(BoxSpawn.class).getBox(), path2, this::finish);
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

    private Vector[] getSnakePath(int offset, Location location) {
        int circle = 1;
        Vector[] path = new Vector[8 * circle];
        int i = 0;
        double y = 4;
        for (int angle = offset; angle < 360 * circle + offset; angle += 45) {
            Location loc = location.clone();
            loc.setX(loc.getX() + (Math.cos(Math.toRadians(angle)) * 4));
            loc.setZ(loc.getZ() + (Math.sin(Math.toRadians(angle)) * 4));
            loc.setY(loc.getY() + y);
            path[i] = loc.toVector();
            i++;
            y -= 0.25;
        }
        return path;
    }
}
