package me.kasper.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.kasper.BuildTraining;
import me.kasper.map.MapManager;

public class GameZone {
    private final double ZX = 4;
    private final double ZY = 10;
    private final double ZZ = 4;

    private final GameManager gameManager;
    private final MapManager manager;

    public GameZone(MapManager manager, GameManager gameManager){
        this.gameManager = gameManager;
        this.manager = manager;
        Bukkit.getScheduler().runTaskTimer(BuildTraining.getInstance(), () -> {
            checkPlayerZone();
        },0,20);
    }

    private void checkPlayerZone(){
        manager.getPlayerMap().forEach((player, cord) -> {
            if (isGameZone(player)){
                gameManager.teleportPlayer(player);
            }
        });
    }

    public boolean isGameZone(Player player){
        Location cord = manager.getPlayerMap().get(player);

        return player.getLocation().getBlockX() >= (cord.getBlockX() + ZX) ||
                player.getLocation().getBlockX() <= (cord.getBlockX() - ZX) ||
                player.getLocation().getBlockZ() <= (cord.getBlockZ() - ZZ) ||
                player.getLocation().getBlockY() <= (cord.getBlockY() - ZY);
    }
    public boolean isPlayZone(Player player){
        Location cord = manager.getPlayerMap().get(player);

        return player.getLocation().getBlockX() >= (cord.getBlockX() + ZX) ||
                player.getLocation().getBlockX() <= (cord.getBlockX() - ZX) ||
                player.getLocation().getBlockZ() <= (cord.getBlockZ() + 2) ||
                player.getLocation().getBlockY() <= (cord.getBlockY() - ZY);
    }
}
