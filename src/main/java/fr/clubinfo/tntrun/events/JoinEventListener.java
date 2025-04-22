package fr.clubinfo.tntrun.events;

import fr.clubinfo.tntrun.Config;
import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.GameStatus;
import fr.clubinfo.tntrun.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class JoinEventListener implements Listener {

    private Main plugin;

    public JoinEventListener(Main main) {

        this.plugin = main;

    }

    @EventHandler
    public void onJoinEvent(org.bukkit.event.player.PlayerJoinEvent event) {

        Game.addPlayer(event.getPlayer());

    }
}
