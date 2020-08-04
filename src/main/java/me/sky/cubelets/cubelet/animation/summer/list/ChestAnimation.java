package me.sky.cubelets.cubelet.animation.summer.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimation;
import me.sky.cubelets.cubelet.animation.handler.CubeletAnimationPart;
import me.sky.cubelets.utils.ITaskSchedule;
import me.sky.cubelets.cubelet.animation.summer.objects.EvocationEntity;
import me.sky.cubelets.cubelet.animation.summer.objects.VexStatue;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.particle.ParticleEffect;
import net.minecraft.server.v1_16_R1.BlockPosition;
import net.minecraft.server.v1_16_R1.EntityIllagerWizard;
import net.minecraft.server.v1_16_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_16_R1.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestAnimation extends CubeletAnimationPart {
    private final EvocationEntity[] evokers = new EvocationEntity[4];
    private final VexStatue[] statues = new VexStatue[4];

    public ChestAnimation(CubeletAnimation animation, Player player, CubeletLocation cubeletLocation, ICubeletsPlugin plugin) {
        super(animation, player, cubeletLocation, plugin);
    }

    @Override
    public void start() {
        super.start();
        getCubeletLocation().getLocation().getBlock().setType(Material.ENDER_CHEST);
        Block block = getCubeletLocation().getLocation().getBlock();
        World serverWorld = ((CraftWorld) block.getWorld()).getHandle();
        BlockPosition pos = new BlockPosition(block.getX(), block.getY(), block.getZ());
        block.getWorld().playSound(block.getLocation(), Sound.BLOCK_METAL_PLACE, 1, 1);
        spawnEvokers();
        for (EvocationEntity evocationEntity : evokers) {
            ParticleEffect.SMOKE_LARGE.display(evocationEntity.getBukkitEntity().getLocation().clone().add(0, 1, 0), 0, 1, 0, 0.25f, 20, null, Bukkit.getOnlinePlayers());
        }
        spawnStatues();
        new BukkitRunnable() {
            @Override
            public void run() {
                block.getWorld().playSound(block.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN, 1, 0);
                serverWorld.playBlockAction(pos, serverWorld.getType(pos).getBlock(), 1, 1);
                for (EvocationEntity evocationEntity : evokers) {
                    evocationEntity.playAnimation(EntityIllagerWizard.Spell.SUMMON_VEX);
                }
                ParticleEffect.SMOKE_LARGE.display(block.getLocation().add(0.5, 1, 0.5), 0, 1, 0, 0.25f, 20, null, Bukkit.getOnlinePlayers());
                scheduleLater(() -> finish(), 20);
            }
        }.runTaskLater(getPlugin(), 40);
    }

    @Override
    public void update() {

    }

    @Override
    public void remove() {
        for (EvocationEntity entity : evokers) {
            entity.getBukkitEntity().remove();
        }
        for (VexStatue entity : statues) {
            entity.getBukkitEntity().remove();
        }
    }

    @Override
    public int getUpdateTime() {
        return 0;
    }

    private void scheduleLater(ITaskSchedule taskSchedule, int delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                taskSchedule.run();
            }
        }.runTaskLater(getPlugin(), delay);
    }

    private void spawnEvokers() {
        World serverWorld = ((CraftWorld) getCubeletLocation().getLocation().getWorld()).getHandle();
        Location center = getCubeletLocation().getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
        evokers[0] = new EvocationEntity(getEvokerLocation(center, 1, 1), center);
        evokers[1] = new EvocationEntity(getEvokerLocation(center, -1, 1), center);
        evokers[2] = new EvocationEntity(getEvokerLocation(center, 1, -1), center);
        evokers[3] = new EvocationEntity(getEvokerLocation(center, -1, -1), center);
        for (EvocationEntity evocationEntity : evokers) {
            serverWorld.addEntity(evocationEntity, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }

    private Location getEvokerLocation(Location center, int x, int z) {
        return center.clone().add(x, 1, z);
    }

    private void spawnStatues() {
        World serverWorld = ((CraftWorld) getCubeletLocation().getLocation().getWorld()).getHandle();
        Location center = getCubeletLocation().getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
        statues[0] = new VexStatue(getStatueLocation(center, 1, 1));
        statues[1] = new VexStatue(getStatueLocation(center, -1, -1));
        statues[2] = new VexStatue(getStatueLocation(center, -1, 1));
        statues[3] = new VexStatue(getStatueLocation(center, 1, -1));
        for (VexStatue vexStatue : statues) {
            serverWorld.addEntity(vexStatue, CreatureSpawnEvent.SpawnReason.CUSTOM);
        }
    }

    private Location getStatueLocation(Location center, int x, int z) {
        return center.clone().add(x * 2, 1, z * 2);
    }
}
