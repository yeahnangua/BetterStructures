package com.magmaguy.betterstructures.listeners;

import com.magmaguy.betterstructures.config.DefaultConfig;
import com.magmaguy.betterstructures.mobtracking.MobTrackingManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.UUID;

/**
 * Listener for mob death events to track structure clearing progress.
 */
public class MobDeathListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        if (!DefaultConfig.isMobTrackingEnabled()) return;

        UUID mobUUID = event.getEntity().getUniqueId();
        MobTrackingManager manager = MobTrackingManager.getInstance();

        // Check if this is a tracked mob
        if (!manager.isTrackedMob(mobUUID)) return;

        // Get the killer (may be null)
        Player killer = event.getEntity().getKiller();

        // Handle mob death
        manager.onMobDeath(mobUUID, killer);
    }
}
