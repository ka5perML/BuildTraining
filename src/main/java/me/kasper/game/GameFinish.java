package me.kasper.game;

import lombok.SneakyThrows;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import me.kasper.BuildTraining;
import me.kasper.map.MapManager;


public class GameFinish{
    private final MapManager manager;
    private final GameManager gameManager;

    public GameFinish(MapManager manager, GameManager gameManager) {
        this.manager = manager;
        this.gameManager = gameManager;
        Bukkit.getScheduler().runTaskTimer(BuildTraining.getInstance(), () -> {
            checkFinish();
        },0,20);
    }

    private void checkFinish(){
        manager.getPlayerMap().forEach((player, cord) -> {
            if (cord != null && player.getLocation().getBlockZ() >= (cord.clone().getBlockZ() + gameManager.getPlayerFinishMAP().get(player))){
                gameManager.teleportPlayer(player);
            }
        });
    }

    public void addDefaultPlayerFinish(Player player){
        gameManager.getPlayerFinishMAP().putIfAbsent(player, 23.0);
        buildFinish(player);
    }

    public void addCustomPlayerFinish(Player player, double distance){
        destroyFinish(player);
        gameManager.getPlayerFinishMAP().put(player, (distance + 3));
        buildFinish(player);
    }

    public void removePlayer(Player player){
        destroyFinish(player);
        gameManager.getPlayerFinishMAP().remove(player);
    }

    @SneakyThrows
    private void buildFinish(Player player){
        Location location = manager.getPlayerMap().get(player);
        for (int dz = 0; dz < 5; dz++) {
            Location blockLocation = new Location(
                    location.getWorld(),
                    location.getX() + dz - 2,
                    location.getY(),
                    location.getZ() + gameManager.getPlayerFinishMAP().get(player)
            );
            location.getWorld().getBlockAt(blockLocation).setType(Material.GOLD_BLOCK);
        }
    }

    @SneakyThrows
    private void destroyFinish(Player player){
        try {
            Location location = manager.getPlayerMap().get(player);
            for (int dz = 0; dz < 5; dz++) {
                Location blockLocation = new Location(
                        location.getWorld(),
                        location.getX() + dz - 2,
                        location.getY(),
                        location.getZ() + gameManager.getPlayerFinishMAP().get(player)
                );
                location.getWorld().getBlockAt(blockLocation).setType(Material.AIR);
            }
        }catch (NullPointerException e){
            Bukkit.getLogger().warning(e.getMessage());
        }
    }
}
