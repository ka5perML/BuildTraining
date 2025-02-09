package org.example.da.buildtraining;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.example.da.buildtraining.game.GameManager;
import org.example.da.buildtraining.game.command.CustomFinish;
import org.example.da.buildtraining.game.listener.PlayerRegistnerListener;
import org.example.da.buildtraining.map.MapManager;
import org.example.da.buildtraining.timer.TimerManager;
import org.example.da.buildtraining.util.CFGPlatformLoader;

public final class BuildTraining extends JavaPlugin {
    private static BuildTraining instance;
    private CFGPlatformLoader loader;
    private MapManager mapManager;
    private GameManager gameManager;
    private TimerManager timerManager;

    @Override
    public void onEnable() {
        instance = this;

        loader = new CFGPlatformLoader();
        mapManager = new MapManager(loader.loadPlatformCFG(getClass().getClassLoader().getResourceAsStream("cfgPlatform.json")));
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
