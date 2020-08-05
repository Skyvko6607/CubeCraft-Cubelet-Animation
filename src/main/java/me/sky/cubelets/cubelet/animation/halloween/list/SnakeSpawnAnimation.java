package me.sky.cubelets.cubelet.animation.halloween.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.cubelet.animation.halloween.objects.snake.SnakeEntity;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SnakeSpawnAnimation extends CubeletAnimationPart {

    private SnakeEntity[] snakes = new SnakeEntity[2];

    public SnakeSpawnAnimation(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(animation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
        getCubeletLocation().getLocation().getWorld().playSound(getCubeletLocation().getLocation(), Sound.ENTITY_EVOKER_PREPARE_ATTACK, 0.5f, 1);
        Location spawn = getCubeletLocation().getLocation().clone().add(0, 1.35, 0);
        snakes[0] = new SnakeEntity(getCubeletAnimation().getCubelet(), spawn.clone(), getPlugin());
        snakes[1] = new SnakeEntity(getCubeletAnimation().getCubelet(), spawn.clone(), getPlugin());
        Vector[] path = getSnakePath(0, spawn.clone());
        Vector[] path2 = getSnakePath(180, spawn.clone());
        MinecraftUtils.scheduleLater(() -> getCubeletLocation().getLocation().getWorld().playSound(getCubeletLocation().getLocation(), Sound.ENTITY_VEX_CHARGE, 1, 0), 10, getPlugin());
        snakes[0].spawn(getCubeletAnimation().getAnimationByClass(BoxSpawn.class).getBox(), path, () -> { });
        snakes[1].spawn(getCubeletAnimation().getAnimationByClass(BoxSpawn.class).getBox(), path2, this::finish);
    }

    @Override
    public void update() {
        World world = snakes[0].getArmorStands()[0].getWorld();
        Entity ent1 = snakes[0].getArmorStands()[0];
        Entity ent2 = snakes[1].getArmorStands()[0];
        world.playSound(ent1.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1);
        world.playSound(ent2.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 0.5f, 1);
    }

    @Override
    public void remove() {

    }

    @Override
    public int getUpdateTime() {
        return 5;
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
