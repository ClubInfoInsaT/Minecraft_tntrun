package fr.clubinfo.tntrun.events;

import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.GameStatus;
import fr.clubinfo.tntrun.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class QuitEventListener implements Listener {

    private final Main plugin;

    public QuitEventListener(Main main) {
        this.plugin = main;
    }

    @EventHandler
    public void onQuitEvent(org.bukkit.event.player.PlayerQuitEvent event) {
        // Handle the player quit event here
        // You can access the player using event.getPlayer()
        // and perform any actions you need
        if (Game.gameStatus == GameStatus.RUNNING) {
            Game.killPlayer(event.getPlayer());
        }else {
            Game.removePlayer(event.getPlayer());
        }
    }
}
