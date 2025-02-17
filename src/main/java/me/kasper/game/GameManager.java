package me.kasper.game;

import lombok.Getter;
import me.kasper.profile.ProfileManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import me.kasper.map.MapManager;

import java.util.HashMap;
import java.util.Map;

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
    @Getter
    private Map<Player, Double> playerFinishMAP = new HashMap<>();

    public GameManager(MapManager mapManager, ProfileManager profileManager) {
        this.mapManager = mapManager;
        this.gameZone = new GameZone(mapManager, this);
        this.gameFinish = new GameFinish(mapManager, this, profileManager);
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
