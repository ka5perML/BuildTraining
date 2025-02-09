package org.example.da.buildtraining.map;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.example.da.buildtraining.BuildTraining;

public class WorldSetting {
    public void startWorldSetting(){
        World world = Bukkit.getServer().getWorld("world");
        world.setStorm(false);
        world.setThundering(false);
        world.setWeatherDuration(Integer.MAX_VALUE);
        Bukkit.getScheduler().runTaskTimer(BuildTraining.getInstance(), () -> {
            world.setTime(6000);
        },0,200);
    }
}
