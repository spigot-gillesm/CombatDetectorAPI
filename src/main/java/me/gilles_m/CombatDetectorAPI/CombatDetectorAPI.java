package me.gilles_m.CombatDetectorAPI;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.cache.CacheBuilder;

import lombok.Getter;
import me.gilles_m.CombatDetectorAPI.mechanics.CombatChecker;
import me.gilles_m.CombatDetectorAPI.mechanics.CombatListener;
import me.gilles_m.CombatDetectorAPI.mechanics.Cooldown;


public class CombatDetectorAPI extends JavaPlugin {

	@Getter
	private static CombatDetectorAPI instance;

	@Override
	public void onEnable() {

		instance = this;

		Bukkit.getConsoleSender().sendMessage(Common.colorize(ChatColor.WHITE + "[" + ChatColor.GREEN + "CombatDetectorAPI" + ChatColor.WHITE + "]" + ChatColor.GREEN + " enabled"));

	}

	@Override
	public void onDisable() {

		instance = null;

	}

	//Function used to activate the listener, the combat checker and set (all) the option variables
	public static void intialize(Long frequency, boolean isPotionFighting) {

		Variables.isPotionFighting = isPotionFighting;

		instance.getServer().getPluginManager().registerEvents(new CombatListener(), instance);
		new CombatChecker(frequency);

	}

	//Function used to activate the listener, the combat checker and set (all) the option variables
	public static void intialize(Long frequency, int duration, boolean isPotionFighting) {

		Variables.isPotionFighting = isPotionFighting;
		Cooldown.setCache(CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.SECONDS).build());

		instance.getServer().getPluginManager().registerEvents(new CombatListener(), instance);
		new CombatChecker(frequency);

	}

	//Function used to activate the listener, the combat checker and set all the option variables
	public static void intialize(Long frequency, int duration, boolean isPotionFighting, boolean anyPotion, List<String> potionList, boolean isSplashFighting, boolean isLingeringFighting) {

		Variables.isPotionFighting = isPotionFighting;
		Variables.anyPotion = anyPotion;
		Variables.potionList = potionList;
		Variables.isSplashFighting = isSplashFighting;
		Variables.isLingeringFighting = isLingeringFighting;
		Cooldown.setCache(CacheBuilder.newBuilder().expireAfterWrite(duration, TimeUnit.SECONDS).build());

		instance.getServer().getPluginManager().registerEvents(new CombatListener(), instance);
		new CombatChecker(frequency);

	}


	//Check if a player is in combat
	public static boolean isInCombat(Player player) {

		return Cooldown.cache.getIfPresent(player.getUniqueId()) != null;

	}


	//Get the list of all the players in combat
	public static List<Player> getPlayers() {

		return Cooldown.playersInCombat;

	}

	public static void removePlayer(Player player) {

		//Removing it from the cache will then remove it from the list on the next tick
		Cooldown.cache.invalidate(player.getUniqueId());

	}

}
