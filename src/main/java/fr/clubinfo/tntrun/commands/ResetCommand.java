package fr.clubinfo.tntrun.commands;

import fr.clubinfo.tntrun.Game;
import fr.clubinfo.tntrun.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ResetCommand implements CommandExecutor {
    public ResetCommand(Main main) {
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if (!commandSender.hasPermission("tntrun.reset"))
            return false;
        Game.resetGame();
        return true;
    }
}
