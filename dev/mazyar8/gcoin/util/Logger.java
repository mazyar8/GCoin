package dev.mazyar8.gcoin.util;

import org.bukkit.Bukkit;

import dev.mazyar8.gcoin.GCoin;

public class Logger {

	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage("[" + GCoin.getGCoin().getName() + "] " + message);
	}
	
}
