package fr.clubinfo.tntrun;

import fr.clubinfo.tntrun.commands.ListCommand;
import fr.clubinfo.tntrun.commands.ResetCommand;
import fr.clubinfo.tntrun.commands.SpectatorCommand;
import fr.clubinfo.tntrun.commands.StartCommand;
import fr.clubinfo.tntrun.events.*;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Game.setPlugin(this);

        // register events
        getServer().getPluginManager().registerEvents(new WalkEventListener(this), this);
        getServer().getPluginManager().registerEvents(new JoinEventListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitEventListener(this), this);
        getServer().getPluginManager().registerEvents(new TakeDamageEventListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerEventListener(this), this);

        // register commands
        getCommand("list").setExecutor(new ListCommand(this));
        getCommand("start").setExecutor(new StartCommand(this));
        //getCommand("stop").setExecutor(new StopCommand(this));
        getCommand("reset").setExecutor(new ResetCommand(this));
        getCommand("spec").setExecutor(new SpectatorCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
