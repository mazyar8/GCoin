package dev.mazyar8.gcoin.config;

import java.lang.reflect.Field;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import dev.mazyar8.gcoin.GCoin;

public class Config {
	
	public static String DATABASE_HOST = "localhost";
	public static String DATABASE_NAME = "GCoin";
	public static String DATABASE_USER = "root";
	public static String DATABASE_PASS = "";
	public static int DATABASE_PORT = 3306;
	public static double DEFAULT_BALANCE_AMOUNT = 1000;
	public static double DEFAULT_BALANCE_MAX = 50000;
	public static String ERROR_COMMAND_NO_PERMISSION = ChatColor.RED + "you don't have permission to use that command.";
	public static String ERROR_COMMAND_JUST_PLAYERS_ALLOW_TO_USE = ChatColor.RED + "just players can use this command.";
	public static String ERROR_SUBCOMMAND_PLYAER_IS_NOT_ONLINE = ChatColor.RED + "that player is not online.";
	public static String ERROR_SUBCOMMAND_INVALID_NUMBER = ChatColor.RED + "invalid number.";
	public static String ERROR_COMMAND_NEGATIVE_NUMBER = ChatColor.RED + "amount should be positive.";
	public static String ERROR_COMMAND_NO_ENOUGH_BALANCE = ChatColor.RED + "you don't have enough coins";
	public static String ERROR_COMMAND_MORE_THAN_MAX_BALANCE = ChatColor.RED + "this amount more than player's max balance.";
	
	public Config(FileConfiguration fc) {
		boolean shouldSave = false;
		for (Field f : getClass().getFields()) {
			String path = f.getName().toLowerCase().replace("_", ".");
			try {
				if (fc.isSet(path)) {
					f.set(this, fc.get(path));
				} else {
					fc.set(path, f.get(this));
					shouldSave = true;
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		if (shouldSave)
			GCoin.getGCoin().save();
	}
	
	public void reload(FileConfiguration fc) {
		for (Field f : getClass().getFields()) {
			String path = f.getName().toLowerCase().replace("_", ".");
			try {
				if (fc.isSet(path))
					f.set(this, fc.get(path));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
	
}
