package fr.clubinfo.tntrun.commands;

import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpectatorCommand implements CommandExecutor {
    public SpectatorCommand(Main main) {
    }


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {

        if (!commandSender.hasPermission("tntrun.spectator"))
            return false;

        if (!(commandSender instanceof Player player))
            return false;

        if (Game.spectators.contains(player)) {
            Game.removeSpectator(player);
            //Game.addPlayer(player);
        }else{
            Game.addSpectator(player);
        }

        return true;
    }
}
