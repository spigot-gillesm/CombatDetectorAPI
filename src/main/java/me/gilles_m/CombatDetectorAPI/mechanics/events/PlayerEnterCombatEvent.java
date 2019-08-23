package me.gilles_m.CombatDetectorAPI.mechanics.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import lombok.Getter;
import me.gilles_m.CombatDetectorAPI.mechanics.Cooldown;

public class PlayerEnterCombatEvent extends Event implements Cancellable {

	private static final HandlerList HANDLERS = new HandlerList();

	private boolean isCancelled;
	@Getter
	private final Player player;

	public PlayerEnterCombatEvent(Player player) {

		this.player = player;

		//We check if the player is not already registered before registering it
		if(!Cooldown.playersInCombat.contains(player))
			Cooldown.playersInCombat.add(player);

	}

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

	@Override
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.isCancelled = cancel;
	}

}
