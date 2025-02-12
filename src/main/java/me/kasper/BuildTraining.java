package me.kasper;

import lombok.SneakyThrows;
import me.kasper.map.Platform;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import me.kasper.game.GameManager;
import me.kasper.game.command.CustomFinish;
import me.kasper.game.listener.PlayerRegistnerListener;
import me.kasper.map.MapManager;
import me.kasper.timer.TimerManager;
import me.kasper.util.CFGPlatformLoader;

import java.util.List;

public final class BuildTraining extends JavaPlugin {
    private static BuildTraining instance;
    private CFGPlatformLoader loader;
    private MapManager mapManager;
    private GameManager gameManager;
    private TimerManager timerManager;
    private List<Platform> platformList;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        loader = new CFGPlatformLoader();

        platformList = loader.loadPlatformCFG(BuildTraining.getInstance().getResource("cfgPlatform.json"));
        mapManager = new MapManager(platformList);
        gameManager = new GameManager(mapManager);
        timerManager = new TimerManager(mapManager, gameManager);
        mapManager.getWorldSetting().startWorldSetting();

        //Command
        getCommand("finish").setExecutor(new CustomFinish(gameManager));

        //Listener
        registerListener(
                new PlayerRegistnerListener(gameManager)
        );

        Bukkit.getLogger().info("[BuildTraining] Online");
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            gameManager.serverOffline(player);
            player.kickPlayer("Reload this server");
        });
        Bukkit.getLogger().info("[BuildTraining] Offline");
    }

    public static BuildTraining getInstance() {
        return instance;
    }

    private void registerListener(Listener... listeners){
        for (Listener listener : listeners){
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }
}
