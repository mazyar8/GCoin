package dev.mazyar8.gcoin.util;

import org.bukkit.command.Command;

public class CommandUtil {

	public static boolean valid(String[] args, int minLength) {
		return args.length > minLength;
	}
	
	public static String getCommandUsage(String format, Command cmd) {
		return format.replace("{command}", cmd.getName());
	}
	
}
