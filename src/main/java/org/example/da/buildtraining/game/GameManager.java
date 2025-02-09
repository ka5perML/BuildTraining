package org.example.da.buildtraining.game;

import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.example.da.buildtraining.map.MapManager;

public class GameManager {
    @Getter
    private final MapManager mapManager;
    @Getter
    private GameZone gameZone;
    @Getter
    private GameFinish gameFinish;
    @Getter
    private GameBuild gameBuild;
    @Getter
    private GameDefaultItems defaultItems;

    public GameManager(MapManager mapManager) {
        this.mapManager = mapManager;
        this.gameFinish = new GameFinish(mapManager, this);
        this.gameZone = new GameZone(mapManager, this);
        this.gameBuild = new GameBuild();
        this.defaultItems = new GameDefaultItems();
    }

    public void serverOffline(Player player){
        gameFinish.removePlayer(player);
        mapManager.end(player);
    }

    public void teleportPlayer(Player player){
        Location location = mapManager.getPlayerMap().get(player).clone().add(0.5,2,0.5);

        defaultItems.giveDefaultItem(player);
        gameBuild.removeBlocks(player);
        player.setFallDistance(0);
        player.teleport(location);

    }
}
