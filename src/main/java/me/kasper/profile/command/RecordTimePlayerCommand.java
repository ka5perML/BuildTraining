package me.kasper.profile.command;

import me.kasper.profile.ProfileManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RecordTimePlayerCommand implements CommandExecutor {
    private final ProfileManager profileManager;

    public RecordTimePlayerCommand(ProfileManager profileManager) {
        this.profileManager = profileManager;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;

        double message = profileManager.getProfileMap().get(player).getBestTime();
        if(message == 0.0) {
            player.sendMessage("Нету рекордов");
            return true;
        }
        player.sendMessage("Лучшее время " + String.format("%.2f", message));
        return true;
    }
}
