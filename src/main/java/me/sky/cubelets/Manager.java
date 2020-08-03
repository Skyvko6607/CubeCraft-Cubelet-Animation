package me.sky.cubelets;

public class Manager<T extends ICubeletsPlugin> {
    private final T plugin;

    public Manager(T plugin) {
        this.plugin = plugin;
    }

    public T getPlugin() {
        return plugin;
    }
}
