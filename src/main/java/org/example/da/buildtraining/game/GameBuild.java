package org.example.da.buildtraining.game;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.example.da.buildtraining.BuildTraining;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBuild {
    @Getter
    private Map<Player, List<Location>> playerBlockMap = new HashMap<>();

    @SneakyThrows
    public void removeBlocks(Player player) {
        List<Location> blocks = playerBlockMap.get(player);
        if (blocks == null || blocks.isEmpty()) return;

        new BukkitRunnable() {
            int index = 0;

            @Override
            public void run() {
                if (index >= blocks.size()) {
                    playerBlockMap.remove(player);
                    cancel();
                    return;
                }

                Location loc = blocks.get(index);
                loc.getBlock().setType(Material.AIR);
                player.playSound(loc, Sound.BLOCK_STONE_BREAK, 1.0f, 2.0f);

                index++;
            }
        }.runTaskTimer(BuildTraining.getInstance(), 0, 2);
    }
}
