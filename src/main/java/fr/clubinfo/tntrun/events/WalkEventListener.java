package fr.clubinfo.tntrun.events;

import fr.clubinfo.tntrun.Config;
import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.GameStatus;
import fr.clubinfo.tntrun.Main;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class WalkEventListener implements Listener {

    private static final Logger log = LoggerFactory.getLogger(WalkEventListener.class);
    private final Main plugin;

    public WalkEventListener(Main main) {

        this.plugin = main;

    }

    @EventHandler
    public void onWalkEvent(org.bukkit.event.player.PlayerMoveEvent event) {
        // Handle the player move event here
        // You can access the player using event.getPlayer()
        // and perform any actions you need
        if (Game.gameStatus == GameStatus.RUNNING) {
            if (event.getPlayer().getLocation().add(new Vector(0,-1,0)).getBlock().getType().equals(Material.TNT)) {
                // wait for 20 ticks and remove the block

                Location loc = event.getPlayer().getLocation().add(new Vector(0,-1,0));
                Block block = loc.getBlock();
               new BukkitRunnable() {
                   @Override
                     public void run() {
                          if (block.getType().equals(Material.TNT)) {
                            block.setType(Material.AIR);
                            Game.blocks.add(loc);
                          }
                     }
               }.runTaskLater(plugin, 15L);

            }
            if (event.getPlayer().getLocation().getBlockY() < 0) {
                Game.killPlayer(event.getPlayer());
            }
        }
    }

}
