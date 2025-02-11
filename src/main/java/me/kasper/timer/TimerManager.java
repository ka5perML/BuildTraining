package me.kasper.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import me.kasper.BuildTraining;
import me.kasper.game.GameManager;
import me.kasper.map.MapManager;

import java.util.HashMap;
import java.util.Map;

public class TimerManager {
    private final Map<Player, Timer> watches = new HashMap<>();
    private final MapManager manager;
    private Timer timer;
    private final GameManager gameManager;

    public TimerManager(MapManager manager, GameManager gameManager){
        this.manager = manager;
        this.gameManager = gameManager;

        Bukkit.getScheduler().runTaskTimer(BuildTraining.getInstance(), () -> {
            manager.getPlayerMap().forEach((player, location) -> {
                checkPlayer(player);
            });
        },0,1);
    }

    public void checkPlayer(Player player) {
        timer = new Timer(player);
        watches.putIfAbsent(player, timer);
        if (!gameManager.getGameZone().isGameZone(player)){
            watches.get(player).startTimer();
        }else
            watches.get(player).stopTimer();
    }
}
