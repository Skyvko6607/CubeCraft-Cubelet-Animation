package me.sky.cubelets.commands.list;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.commands.CubeCommand;
import me.sky.cubelets.cubelet.objects.Cubelet;
import me.sky.cubelets.location.CubeletLocation;
import me.sky.cubelets.utils.MinecraftUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CubeletCommand extends CubeCommand {
    public CubeletCommand(String command, ICubeletsPlugin plugin) {
        super(command, plugin);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by a player!");
            return false;
        }
        Player player = (Player) commandSender;
        if (strings.length == 0) {
            sendHelpMessage(player);
            return false;
        }
        if (strings[0].equalsIgnoreCase("list")) {
            player.sendMessage(new String[] {
                    "",
                    MinecraftUtils.centerText("§6§lCubelet Commands"),
            });
            getPlugin().getCubeletManager().getCubelets().forEach(cubelet -> player.sendMessage(String.format("   §f- §7%s §f| %s", cubelet.getId(), cubelet.getName())));
            player.sendMessage("");
            return true;
        } else if (strings[0].equalsIgnoreCase("give") || strings[0].equalsIgnoreCase("revoke")) {
            Player target = Bukkit.getPlayer(strings[1]);
            if (target == null || !target.isOnline()) {
                player.sendMessage(getPlugin().getMessages().getMessage("PlayerNotOnline"));
                return false;
            }
            Cubelet cubelet = getPlugin().getCubeletManager().getCubeletById(strings[2]);
            if (cubelet == null) {
                player.sendMessage(getPlugin().getMessages().getMessage("CubeletNotExist"));
                return false;
            }
            if (strings[0].equalsIgnoreCase("give")) {
                cubelet.start(player, new CubeletLocation("test", player.getLocation(), getPlugin()));
                player.sendMessage("Test");
                return true;
            } else if (strings[0].equalsIgnoreCase("revoke")) {
                return true;
            }
        }
        sendHelpMessage(player);
        return false;
    }

    private void sendHelpMessage(Player player) {
        player.sendMessage(new String[] {
                "",
                MinecraftUtils.centerText("§6§lCubelet Commands"),
                "   §f- §7/cubelet list",
                "   §f- §7/cubelet give <player> <cubelet>",
                "   §f- §7/cubelet revoke <player> <cubelet>",
                "",
        });
    }
}
