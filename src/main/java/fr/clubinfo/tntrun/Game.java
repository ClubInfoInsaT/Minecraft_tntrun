package fr.clubinfo.tntrun;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public static Main plugin;

    public static GameStatus gameStatus = GameStatus.WAITING;

    public static int playerCount = 0;
    public static int playerNotDead = 0;
    public static List<Player> players = new ArrayList<Player>();
    public static List<Player> spectators = new ArrayList<Player>();
    public static Map<Player, Integer> playersScore = new HashMap<Player, Integer>();
    public static List<Location> blocks = new ArrayList<Location>();

    public static void setPlugin(Main main) {
        plugin = main;
    }

    public static void resetGame() {
        gameStatus = GameStatus.WAITING;
        playerNotDead = 0;
        playersScore.clear();

        for (Player player : players) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
            player.setGameMode(GameMode.ADVENTURE);
        }

        for (Location loc : blocks) {
            loc.getBlock().setType(Material.TNT);
        }
    }

    public static void addPlayer(Player player) {
        if (gameStatus == GameStatus.WAITING) {
            if (players.contains(player)) {
                player.sendMessage(Config.COLOR_ERROR + "Vous êtes déjà dans la partie !");
                return;
            }
            player.sendMessage(Config.COLOR_INFO + "Vous avez rejoint la partie !");
            players.add(player);
            playerCount++;
            playerNotDead++;
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
            player.setGameMode(GameMode.ADVENTURE);
        }
    }

    public static void removePlayer(Player player) {
        if (gameStatus == GameStatus.WAITING) {
            if (!players.contains(player)) {
                player.sendMessage(Config.COLOR_ERROR + "Vous n'êtes pas dans la partie !");
                return;
            }
            player.sendMessage(Config.COLOR_INFO + "Vous avez quitté la partie !");
            players.remove(player);
            playerCount--;
            playerNotDead--;
        }
    }

    public static void killPlayer(Player player) {
        if (gameStatus == GameStatus.RUNNING) {
            if (!players.contains(player)) {
                player.sendMessage(Config.COLOR_ERROR + "Vous n'êtes pas dans la partie !");
                return;
            }
            if (playersScore.containsKey(player)) {
                player.sendMessage(Config.COLOR_ERROR + "Vous êtes déjà mort !");
                return;
            }
            player.sendMessage(Config.COLOR_INFO + "Vous êtes tombé, vous avez perdu !");
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
            playersScore.put(player, playerNotDead);
            playerNotDead--;

            if (playerNotDead <= 1) {
                endGame();
            }
        }
    }

    public static void addSpectator(Player player) {
        if (gameStatus != GameStatus.WAITING)
            return;
        if (spectators.contains(player)) {
            player.sendMessage(Config.COLOR_ERROR + "Vous êtes déjà spectateur !");
            return;
        }
        Game.removePlayer(player);
        spectators.add(player);
        player.setGameMode(GameMode.SPECTATOR);
        player.sendMessage(Config.COLOR_INFO + "Vous êtes maintenant spectateur !");
        player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
    }

    public static void removeSpectator(Player player) {
        if (gameStatus != GameStatus.WAITING)
            return;
        if (!spectators.contains(player)) {
            player.sendMessage(Config.COLOR_ERROR + "Vous n'êtes pas spectateur !");
            return;
        }
        spectators.remove(player);
        Game.addPlayer(player);
    }

    public static void startGame() {
        if (gameStatus != GameStatus.WAITING)
            return;
        gameStatus = GameStatus.STARTING;
        playerNotDead = playerCount;

        for (Player player : players) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 100, 0));
            player.setGameMode(GameMode.ADVENTURE);
        }

        // Countdown 5 to 1 with delay
        new BukkitRunnable() {
            int count = 5;

            @Override
            public void run() {
                if (count > 0) {
                    for (Player player : players) {
                        player.sendTitle("§c" + count, "", 10, 20, 10); // §c = rouge
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1f, 1f);
                    }
                    count--;
                } else {
                    for (Player player : players) {
                        player.sendTitle("§aGO!", "", 10, 20, 10);
                        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                    }
                    gameStatus = GameStatus.RUNNING;
                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0L, 20L); // 20 ticks = 1 seconde
    }

    public static void endGame() {
        if (gameStatus != GameStatus.RUNNING)
            return;
        gameStatus = GameStatus.ENDING;

        // add last player to the score
        for (Player player : Game.players) {
            if (!Game.playersScore.containsKey(player)) {
                Game.playersScore.put(player, Game.playerNotDead);
            }
        }

        // teleport all players to the spawn
        for (Player player : Game.players) {
            player.teleport(new Location(Bukkit.getWorld("world"), 0, 120, 0));
            player.setGameMode(GameMode.SPECTATOR);
        }

        // print the scores to all online players (use the plugin.getServer().getOnlinePlayers() method)
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            player.sendMessage(Config.COLOR_INFO + "Classement : ");

            // create a list of players sorted by score
            List<Player> sortedPlayers = new ArrayList<>(Game.playersScore.keySet());
            sortedPlayers.sort((p1, p2) -> Game.playersScore.get(p2).compareTo(Game.playersScore.get(p1)));
            for (Player sortedPlayer : sortedPlayers) {
                player.sendMessage(Config.COLOR_INFO + "#" + (Game.playersScore.get(sortedPlayer) + " " + sortedPlayer.getName()));
            }

        }

        gameStatus = GameStatus.ENDED;
    }



}
