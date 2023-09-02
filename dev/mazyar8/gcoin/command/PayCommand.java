package dev.mazyar8.gcoin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mazyar8.gcoin.GCoin;
import dev.mazyar8.gcoin.balance.BalanceManager;
import dev.mazyar8.gcoin.config.Config;
import dev.mazyar8.gcoin.permissions.Permissions;
import dev.mazyar8.gcoin.util.CommandUtil;
import dev.mazyar8.gcoin.util.NumberUtil;

public class PayCommand implements CommandExecutor {
	
	private BalanceManager balanceManager = GCoin.getGCoin().getBalanceManager();
	private String COMMAND_USAGE_FORMAT = "/{command} <player> <amount>";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String message, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(Config.ERROR_COMMAND_JUST_PLAYERS_ALLOW_TO_USE);
			return false;
		}
		
		if (!sender.hasPermission(Permissions.COMMAND_PAY)) {
			sender.sendMessage(Config.ERROR_COMMAND_NO_PERMISSION);
			return false;
		}
		
		if (!CommandUtil.valid(args, 1)) {
			sender.sendMessage(CommandUtil.getCommandUsage(this.COMMAND_USAGE_FORMAT, cmd));
			return false;
		}
		
		Player target = Bukkit.getPlayer(args[0]);
		if (target == null) {
			sender.sendMessage(Config.ERROR_SUBCOMMAND_PLYAER_IS_NOT_ONLINE);
			return false;
		}
		
		if (!NumberUtil.isDouble(args[1])) {
			sender.sendMessage(Config.ERROR_SUBCOMMAND_INVALID_NUMBER);
			return false;
		}
		
		double amount = Double.parseDouble(args[1]);
		if (amount <= 0) {
			sender.sendMessage(Config.ERROR_COMMAND_NEGATIVE_NUMBER);
			return false;
		}
		
		Player player = (Player) sender;
		if (balanceManager.getPlayerBalance(player).getAmount() < amount) {
			player.sendMessage(Config.ERROR_COMMAND_NO_ENOUGH_BALANCE);
			return false;
		}
		
		if (balanceManager.getPlayerBalance(target).getAmount() + amount > balanceManager.getPlayerMaxBalance(target)) {
			sender.sendMessage(Config.ERROR_COMMAND_MORE_THAN_MAX_BALANCE);
			return false;
		}
		
		// subtract amount from the player's balance and add amount to the target's balance
		balanceManager.subtractFromPlayerBalance(player, amount);
		balanceManager.addToPlayerBalance(target, amount);
		player.sendMessage(ChatColor.GOLD + "" + amount + (amount == 1 ? " coin" : " coins") + " sent to " + target.getName());
		target.sendMessage(ChatColor.GREEN + "received " + amount + (amount == 1 ? " coin" : " coins") + " from " + player.getName());
		return true;
	}

}
