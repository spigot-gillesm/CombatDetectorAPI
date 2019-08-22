package me.gilles_m.CombatDetectorAPI;

import java.util.ArrayList;
import java.util.List;

public class Variables {

	//Options are by default set to false to prevent any null pointer exception
	public static boolean isPotionFighting, isLingeringFighting, isSplashFighting = false;
	///anyPotion is set to true by default to prevent issues
	public static boolean anyPotion = true;
	//The list is initialize by default to prevent any null pointer exception
	public static List<String> potionList = new ArrayList<String>();

}
