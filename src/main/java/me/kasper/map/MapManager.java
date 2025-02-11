package me.kasper.map;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MapManager {
    private final Location START_LOCATION = new Location(Bukkit.getWorld("world"), 0,200,0);
    @Getter
    private Map<Player, Location> playerMap = new HashMap<>();
    @Getter
    private final List<Platform> platformList;
    @Getter
    private WorldSetting worldSetting;

    public MapManager(List<Platform> platformList) {
        this.platformList = platformList;
        this.worldSetting = new WorldSetting();
    }

    public void start(Player player){
        Location location = getFreeLocation();
        registerPlayer(player, location);
        build(player);
    }

    public void end(Player player){
        destroy(player);
        unregisterPlayer(player);
    }

    private void registerPlayer(Player player, Location location){
        playerMap.put(player, location);
    }

    private void unregisterPlayer(Player player){
        playerMap.remove(player);
    }

    private Location getFreeLocation(){
        int jump = 10;
        Location location = START_LOCATION.clone();
        while (true){
            if (!playerMap.values().contains(location)) {
                Bukkit.getLogger().info(location.toString());
                return location;
            }
            location  = location.add(jump,0,0);
        }
    }

    private Boolean isPlayerCoordinates(Location location){
        for (Location loc : playerMap.values()) {
            if (loc.equals(location)) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows
    private void build(Player player) {
        Location location = playerMap.get(player);

        List<List<String>> blocks = platformList.get(randomPlatform()).blocks;
        for (int dx = 0; dx < blocks.size(); dx++) {
            for (int dz = 0; dz < blocks.get(dx).size(); dz++) {
                Material material = Material.getMaterial(blocks.get(randomPlatform()).get(dz));
                if (material != null) {
                    Location blockLocation = new Location(
                            location.getWorld(),
                            location.getX() + dx - 2,
                            location.getY(),
                            location.getBlockZ() + dz - 2
                    );
                    location.getWorld().getBlockAt(blockLocation).setType(material);
                }
            }
        }
    }

    @SneakyThrows
    private void destroy(Player player) {
        try {
            Location location = playerMap.get(player);
            int width = 5;
            int depth = 5;

            for (int dx = 0; dx < width; dx++) {
                for (int dz = 0; dz < depth; dz++) {
                    Location blockLocation = new Location(
                            location.getWorld(),
                            location.getX() + dx - 2,
                            location.getY(),
                            location.getZ() + dz - 2
                    );
                    location.getWorld().getBlockAt(blockLocation).setType(Material.AIR);
                }
            }
        }catch (NullPointerException e) {
            Bukkit.getLogger().warning(e.getMessage());
        }
    }

    @SneakyThrows
    private int randomPlatform(){
        Random random = new Random();
        return random.nextInt(platformList.size());
    }
}
