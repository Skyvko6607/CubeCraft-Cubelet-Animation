package me.sky.cubelets.cubelet.animation.halloween.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import me.sky.cubelets.utils.particle.ParticleEffect;
import me.sky.cubelets.utils.particle.data.color.RegularColor;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Consumer;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ChainsEffect extends CubeletAnimationPart {
    private final ArmorStand[] chains = new ArmorStand[6];
    private final Location center;
    private final List<Vector> particles = new ArrayList<>();

    public ChainsEffect(CubeletAnimation cubeletAnimation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(cubeletAnimation, player, cubeletLocation, plugin);
        center = getCubeletLocation().getLocation().clone().add(0, 4.1 - 1.2, 0);
    }

    @Override
    public void start() {
        super.start();
        double distance = 0.3;
        chains[0] = spawnChain(center.clone().add(0, 0.2, 0), armorStand -> {
            armorStand.setRotation(45, 0);
            armorStand.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));;
        });
        chains[1] = spawnChain(center.clone().add(0, 0.2, 0), armorStand -> {
            armorStand.setRotation(135, 0);
            armorStand.setHeadPose(new EulerAngle(Math.toRadians(90), 0, 0));
        });
        chains[2] = spawnSideChains(center.clone().add(distance, 0, distance));
        chains[3] = spawnSideChains(center.clone().add(-distance, 0, distance));
        chains[4] = spawnSideChains(center.clone().add(distance, 0, -distance));
        chains[5] = spawnSideChains(center.clone().add(-distance, 0, -distance));
        MinecraftUtils.scheduleLater(this::finish, 5, getPlugin());
        for (double y = 0; y < 10; y += 0.5) {
            particles.add(center.toVector().add(new Vector(0, y + 1.5, 0)));
        }
        center.getWorld().playSound(center, Sound.BLOCK_CHAIN_PLACE, 0.5f, 0);
    }

    @Override
    public void update() {
        particles.forEach(vector -> ParticleEffect.REDSTONE.display(vector.toLocation(center.getWorld()), new RegularColor(145, 145, 145), Bukkit.getOnlinePlayers()));
    }

    @Override
    public void remove() {
        for (ArmorStand chain : chains) {
            chain.remove();
        }
    }

    @Override
    public int getUpdateTime() {
        return 4;
    }

    private ArmorStand spawnChain(Location center, Consumer<ArmorStand> todo) {
        ArmorStand armorStand = center.getWorld().spawn(center, ArmorStand.class, stand -> {
            stand.setSilent(true);
            stand.setVisible(false);
            stand.addScoreboardTag("CubeletEntity");
            stand.setGravity(false);
            todo.accept(stand);
        });
        armorStand.getEquipment().setHelmet(new ItemStack(Material.IRON_TRAPDOOR));
        return armorStand;
    }

    private ArmorStand spawnSideChains(Location center) {
        return spawnChain(center, armorStand -> {
            Location loc = MinecraftUtils.getLookAtLocation(armorStand, chains[0].getLocation());
            armorStand.setRotation(loc.getYaw(), 0);
            armorStand.setHeadPose(new EulerAngle(Math.toRadians(loc.getPitch()), Math.toRadians(45), 0));
        });
    }
}
