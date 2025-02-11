package me.kasper.game.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import me.kasper.game.GameManager;

import java.util.ArrayList;

public class PlayerRegistnerListener implements Listener {
    private final GameManager gameManager;

    public PlayerRegistnerListener(GameManager gamaManager) {
        this.gameManager = gamaManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        gameManager.getMapManager().start(player);
        gameManager.teleportPlayer(player);
        gameManager.getGameFinish().addDefaultPlayerFinish(player);
        e.setJoinMessage("");
    }

    @EventHandler
    public void onPlayerQuiet(PlayerQuitEvent e){
        Player player = e.getPlayer();
        gameManager.getGameFinish().removePlayer(player);
        gameManager.getMapManager().end(player);
        e.setQuitMessage("");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(gameManager.getGameZone().isGameZone(player)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        Player player = e.getPlayer();
        Location blockLocation = e.getBlockPlaced().getLocation();

        if(gameManager.getGameZone().isGameZone(player)){
            e.setCancelled(true);
            return;
        }

        gameManager.getGameBuild().getPlayerBlockMap().putIfAbsent(player, new ArrayList<>());

        gameManager.getGameBuild().getPlayerBlockMap().get(player).add(blockLocation);
    }
}
