package me.gilles_m.CombatDetectorAPI.mechanics;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.gilles_m.CombatDetectorAPI.CombatDetectorAPI;

public class CombatChecker {

	public CombatChecker(Long frequence) {

		System.out.println("CombatChecker initialized with " + frequence);

		new BukkitRunnable() {

			List<Player> previousPlayers = Cooldown.getPlayers();

			@Override
			public void run() {

				final List<Player> currentPlayers = Cooldown.getPlayers();

				//We compare the previous players with the current ones
				for(final Player player : previousPlayers)
					//If a player from previous players is not in the current players, it means he just left the cache. We can then call the PlayerLeaveCombatEvent
					if(!currentPlayers.contains(player)) {

						/*
						 * We instantiate the event and then calls it for the player that was missing from the current players
						 */

						final PlayerLeaveCombatEvent event = new PlayerLeaveCombatEvent(player);
						Bukkit.getServer().getPluginManager().callEvent(event);

					}

				previousPlayers = currentPlayers;

			}

		}.runTaskTimer(CombatDetectorAPI.getInstance(), 0L, frequence);

	}

}
