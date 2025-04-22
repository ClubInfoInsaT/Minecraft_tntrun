package fr.clubinfo.tntrun.commands;

import fr.clubinfo.tntrun.Config;
import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ListCommand implements CommandExecutor {
    public ListCommand(Main main) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!commandSender.hasPermission("tntrun.list"))
            return false;

        commandSender.sendMessage(Config.COLOR_INFO + "List of players in the game:");
        for (Player player : Game.players) {
            commandSender.sendMessage(Config.COLOR_INFO + " - " + player.getName());
        }

        return true;
    }
}
