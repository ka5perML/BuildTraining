package org.example.da.buildtraining.game.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.example.da.buildtraining.game.GameManager;

public class CustomFinish implements CommandExecutor {
    private final GameManager gameManager;

    public CustomFinish(GameManager gameManager) {
        this.gameManager = gameManager;
    }


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;

        Player player = (Player) commandSender;

        if (strings[0] != null) {
            double distance = Double.parseDouble(strings[0]);

            if (distance >= 10.0 && distance <= 100.0) {
                gameManager.getGameFinish().addCustomPlayerFinish(player, distance);
                player.sendMessage("Вы успешно изменили дистанцию финиша на " + (int) distance);
                return true;
            }
        }
        return false;
    }
}
