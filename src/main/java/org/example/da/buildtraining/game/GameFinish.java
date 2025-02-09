package org.example.da.buildtraining.game;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.example.da.buildtraining.BuildTraining;
import org.example.da.buildtraining.map.MapManager;

import java.util.HashMap;
import java.util.Map;

public class GameFinish{
    private final MapManager manager;
    private final GameManager gameManager;
    @Getter
    private Map<Player, Double> playerFinishMAP = new HashMap<>();

    public GameFinish(MapManager manager, GameManager gameManager) {
        this.manager = manager;
        this.gameManager = gameManager;
        Bukkit.getScheduler().runTaskTimer(BuildTraining.getInstance(), () -> {
            checkFinish();
        },0,20);
    }

    private void checkFinish(){
        manager.getPlayerMap().forEach((player, cord) -> {
            if (player.getLocation().getBlockZ() >= (cord.clone().getBlockZ() + playerFinishMAP.get(player))){
                gameManager.teleportPlayer(player);
            }
        });
    }

    public void addDefaultPlayerFinish(Player player){
        playerFinishMAP.putIfAbsent(player, 23.0);
        buildFinish(player);
    }

    public void addCustomPlayerFinish(Player player, double distance){
        destroyFinish(player);
        playerFinishMAP.put(player, (distance + 3));
        buildFinish(player);
    }

    public void removePlayer(Player player){
        destroyFinish(player);
        playerFinishMAP.remove(player);
    }

    @SneakyThrows
    private void buildFinish(Player player){
        Location location = manager.getPlayerMap().get(player);
        for (int dz = 0; dz < 5; dz++) {
            Location blockLocation = new Location(
                    location.getWorld(),
                    location.getX() + dz - 2,
                    location.getY(),
                    location.getZ() + playerFinishMAP.get(player)
            );
            location.getWorld().getBlockAt(blockLocation).setType(Material.GOLD_BLOCK);
        }
    }

    private void destroyFinish(Player player){
        try {
            Location location = manager.getPlayerMap().get(player);
            for (int dz = 0; dz < 5; dz++) {
                Location blockLocation = new Location(
                        location.getWorld(),
                        location.getX() + dz - 2,
                        location.getY(),
                        location.getZ() + playerFinishMAP.get(player)
                );
                location.getWorld().getBlockAt(blockLocation).setType(Material.AIR);
            }
        }catch (NullPointerException e){
            Bukkit.getLogger().warning(e.getMessage());
        }
    }
}
