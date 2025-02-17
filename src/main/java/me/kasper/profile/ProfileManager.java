package me.kasper.profile;

import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ProfileManager {
    @Getter
    private Map<Player, Profile> profileMap = new HashMap<>();

    public void addProfile(Player player, Profile profile) {
        profileMap.put(player, profile);
    }

    public void updateProfile(Player player, double time){
        profileMap.get(player).addTime(time);
    }

    public void reloadProfile(Player player){
        profileMap.get(player).toNullRecord();
    }

    public boolean hasProfile(Player player) {
        return profileMap.containsKey(player);
    }

    public void removeProfile(Player player) {
        profileMap.remove(player);
    }

    public void clearProfiles() {
        profileMap.clear();
    }

    public int getProfileCount() {
        return profileMap.size();
    }
}
