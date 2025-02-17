package me.kasper.game.listener;

import me.kasper.profile.Profile;
import me.kasper.profile.ProfileManager;
import org.bukkit.GameMode;
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
    private final ProfileManager profileManager;
    public PlayerRegistnerListener(GameManager gamaManager, ProfileManager profileManager) {
        this.gameManager = gamaManager;
        this.profileManager = profileManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();

        profileManager.addProfile(player, new Profile(player.getDisplayName(), new ArrayList<>()));
        gameManager.getMapManager().start(player);
        gameManager.teleportPlayer(player);
        gameManager.getGameFinish().addDefaultPlayerFinish(player);
        e.setJoinMessage("");
    }

    @EventHandler
    public void onPlayerQuiet(PlayerQuitEvent e){
        Player player = e.getPlayer();

        profileManager.removeProfile(player);
        gameManager.getGameFinish().removePlayer(player);
        gameManager.getMapManager().end(player);
        e.setQuitMessage("");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        if(!player.getGameMode().equals(GameMode.CREATIVE)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        Player player = e.getPlayer();
        Location blockLocation = e.getBlockPlaced().getLocation();

        if(gameManager.getGameZone().isPlayZone(player)){
            e.setCancelled(true);
            return;
        }

        gameManager.getGameBuild().getPlayerBlockMap().putIfAbsent(player, new ArrayList<>());

        gameManager.getGameBuild().getPlayerBlockMap().get(player).add(blockLocation);
    }
}
