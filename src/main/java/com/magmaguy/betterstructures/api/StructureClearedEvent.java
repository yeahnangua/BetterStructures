package com.magmaguy.betterstructures.api;

import com.magmaguy.betterstructures.structurelocation.StructureLocationData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Event fired when all mobs in a structure have been killed.
 */
public class StructureClearedEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean isCancelled = false;

    private final StructureLocationData structure;
    private final Player clearingPlayer;
    private final long clearTime;

    public StructureClearedEvent(StructureLocationData structure, Player clearingPlayer) {
        this.structure = structure;
        this.clearingPlayer = clearingPlayer;
        this.clearTime = System.currentTimeMillis();
    }

    /**
     * Get the structure that was cleared.
     */
    public StructureLocationData getStructure() {
        return structure;
    }

    /**
     * Get the player who killed the last mob (may be null).
     */
    public Player getClearingPlayer() {
        return clearingPlayer;
    }

    /**
     * Get the timestamp when the structure was cleared.
     */
    public long getClearTime() {
        return clearTime;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.isCancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
