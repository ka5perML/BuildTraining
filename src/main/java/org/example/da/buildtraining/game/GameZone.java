package org.example.da.buildtraining.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.example.da.buildtraining.BuildTraining;
import org.example.da.buildtraining.map.MapManager;

public class GameZone {
    private final double ZX = 4;
    private final double ZY = 15;
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
            if (player.getLocation().getBlockX() >= (cord.clone().getBlockX() + ZX) ||
                    player.getLocation().getBlockX() <= (cord.clone().getBlockX() - ZX) ||
                    player.getLocation().getBlockZ() <= (cord.clone().getBlockZ() - ZZ) ||
                    player.getLocation().getBlockY() <= (cord.clone().getBlockY() - ZY) ||
                    player.getLocation().getBlockY() >= (cord.clone().getBlockY() + ZY)){
                gameManager.teleportPlayer(player);
            }
        });
    }

    public boolean isGameZone(Player player){
        Location cord = manager.getPlayerMap().get(player);
        if (player.getLocation().getBlockX() >= (cord.clone().getBlockX() + ZX) ||
                player.getLocation().getBlockX() <= (cord.clone().getBlockX() - ZX) ||
                player.getLocation().getBlockZ() <= (cord.clone().getBlockZ() + 2) ||
                player.getLocation().getBlockY() <= (cord.clone().getBlockY() - ZY) ||
                player.getLocation().getBlockY() >= (cord.clone().getBlockY() + ZY)) {
            return true;
        }
        return false;
    }
}
