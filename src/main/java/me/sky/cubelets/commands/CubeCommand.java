package me.sky.cubelets.commands;

import me.sky.cubelets.ICubeletsPlugin;
import me.sky.cubelets.Manager;
import org.bukkit.command.CommandExecutor;

public abstract class CubeCommand extends Manager<ICubeletsPlugin> implements CommandExecutor {
    private final String command;
    public CubeCommand(String command, ICubeletsPlugin plugin) {
        super(plugin);
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
