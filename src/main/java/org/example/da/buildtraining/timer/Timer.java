package org.example.da.buildtraining.timer;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.example.da.buildtraining.BuildTraining;

import java.util.Locale;

public class Timer {
    private long startTime;
    private boolean running = false;
    private int taskID;
    private final Player player;

    public Timer(Player player) {
        this.player = player;
    }

    public void startTimer(){
        if (running) return;
        running = true;
        startTime = System.currentTimeMillis();

        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(BuildTraining.getInstance(), () -> {
            long firsTime = System.currentTimeMillis() - startTime;
            double seconds = firsTime / 1000.0;

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR,
                    new TextComponent("§a§lСекундомер: §e" + String.format(Locale.US, "%.2f", seconds) + " сек."));
        }, 0L, 1L);
    }

    public void stopTimer() {
        if (!running) return;
        running = false;
        Bukkit.getScheduler().cancelTask(taskID);
        long elapsed = System.currentTimeMillis() - startTime;
        double seconds = elapsed / 1000.0;
        player.sendMessage("§a§lВремя: §e" + String.format("%.2f", seconds) + " сек.");
    }
}
