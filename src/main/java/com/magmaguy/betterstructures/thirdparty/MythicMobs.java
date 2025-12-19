package com.magmaguy.betterstructures.thirdparty;

import com.magmaguy.magmacore.util.Logger;
import io.lumine.mythic.api.mobs.MythicMob;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.mobs.ActiveMob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

/**
 * Support for MythicMobs, configuration format is "MobID[:level]"
 *
 * @author CarmJos
 */
public class MythicMobs {

    public static boolean Spawn(Location location, String filename) {
        return spawnAndReturn(location, filename) != null;
    }

    /**
     * Spawns a MythicMob at the set location and returns the spawned entity.
     *
     * @param location Location where the mob should spawn
     * @param filename MobID[:level] format string
     * @return The spawned Entity, or null if spawn failed
     */
    public static Entity spawnAndReturn(Location location, String filename) {
        if (Bukkit.getPluginManager().getPlugin("MythicMobs") == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasPermission("betterstructures.*")) {
                    Logger.sendMessage(player, "&cOne of your packs uses the MythicMobs plugin &4but MythicMobs is not currently installed on your server&c! &2You can download it here: &9https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1-custom-mob-creator%E2%97%84.5702/");
                }
            }
            return null;
        }

        String[] args = filename.split(":");

        String mobId = args[0];
        MythicMob mob = MythicBukkit.inst().getMobManager().getMythicMob(mobId).orElse(null);
        if (mob == null) {
            Logger.warn("Failed to spawn regional boss! Could not find MythicMob with ID: '" + mobId + "'");
            Logger.warn("  - Original filename parameter: " + filename);
            Logger.warn("  - Location: " + location.getWorld().getName() + " at " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ());
            Logger.warn("  - Make sure a mob with this exact ID exists in ~/plugins/MythicMobs/Mobs/");
            return null;
        }

        double level;
        try {
            level = Double.parseDouble(args[1]);
        } catch (Exception e) {
            Logger.warn("Failed to parse level for mob " + filename + "!");
            return null;
        }

        ActiveMob activeMob = mob.spawn(BukkitAdapter.adapt(location), Math.max(1, level));
        if (activeMob != null && activeMob.getEntity() != null) {
            return activeMob.getEntity().getBukkitEntity();
        }
        return null;
    }

}
