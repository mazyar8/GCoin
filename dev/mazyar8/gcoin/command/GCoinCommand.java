package dev.mazyar8.gcoin.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.mazyar8.gcoin.GCoin;
import dev.mazyar8.gcoin.config.Config;
import dev.mazyar8.gcoin.permissions.Permissions;
import dev.mazyar8.gcoin.util.CommandUtil;
import dev.mazyar8.gcoin.util.NumberUtil;

public class GCoinCommand implements CommandExecutor {
	
	private String COMMAND_USAGE_FORMAT = "/{command} <add/balance/reload/remove>";
	private String SUBCOMMAND_ADD_USAGE_FORMAT = "/{command} add <player> <amount>";
	private String SUBCOMMAND_BALANCE_USAGE_FORMAT = "/{command} balance <player>";
	private String SUBCOMMAND_REMOVE_USAGE_FORMAT = "/{command} remove <player> <amount>";
	
	private GCoin gc = GCoin.getGCoin();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String message, String[] args) {
		if (CommandUtil.valid(args, 0)) {
			switch (args[0].toLowerCase()) {
			case "add":
				if (sender.hasPermission(Permissions.COMMAND_GCOIN_ADD)) {
					if (CommandUtil.valid(args, 2)) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p != null) {
							if (NumberUtil.isDouble(args[2])) {
								double a = Double.parseDouble(args[2]);
								if (gc.getBalanceManager().getPlayerBalance(p).getAmount() + a <= gc.getBalanceManager().getPlayerMaxBalance(p)) {
									gc.getBalanceManager().addToPlayerBalance(p, a);
									sender.sendMessage(ChatColor.GREEN + "" + a + (a == 1 ? " coin" : " coins") + " successfully added to " + p.getName() + "'s balance.");
								} else {
									sender.sendMessage(Config.ERROR_COMMAND_MORE_THAN_MAX_BALANCE);
									return false;
								}
							} else {
								sender.sendMessage(Config.ERROR_SUBCOMMAND_INVALID_NUMBER);
							}
						}else {
							sender.sendMessage(Config.ERROR_SUBCOMMAND_PLYAER_IS_NOT_ONLINE);
						}
					}else {
						sender.sendMessage(CommandUtil.getCommandUsage(this.SUBCOMMAND_ADD_USAGE_FORMAT, cmd));
					}
				}else {
					sender.sendMessage(Config.ERROR_COMMAND_NO_PERMISSION);
				}
				break;
			case "balance":
				if (sender.hasPermission(Permissions.COMMAND_GCOIN_BALANCE)) {
					if (CommandUtil.valid(args, 1)) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p != null) {
							double a = gc.getBalanceManager().getPlayerBalance(p).getAmount();
							sender.sendMessage(p.getName() + "'s balance: " + a + (a == 1 ? " coin" : " coins"));
						} else {
							sender.sendMessage(Config.ERROR_SUBCOMMAND_PLYAER_IS_NOT_ONLINE);
						}
					} else {
						sender.sendMessage(CommandUtil.getCommandUsage(this.SUBCOMMAND_BALANCE_USAGE_FORMAT, cmd));
					}
				}else {
					sender.sendMessage(CommandUtil.getCommandUsage(Config.ERROR_COMMAND_NO_PERMISSION, cmd));
				}
				break;
			case "reload":
				if (sender.hasPermission(Permissions.COMMAND_GCOIN_RELOAD)) {
					gc.reload();
					sender.sendMessage(ChatColor.GREEN + "config reloaded successfully.");
				} else {
					sender.sendMessage(Config.ERROR_COMMAND_NO_PERMISSION);
				}
				break;
			case "remove":
				if (sender.hasPermission(Permissions.COMMAND_GCOIN_REMOVE)) {
					if (CommandUtil.valid(args, 2)) {
						Player p = Bukkit.getPlayer(args[1]);
						if (p != null) {
							if (NumberUtil.isDouble(args[2])) {
								double a = Double.parseDouble(args[2]), currentPlayerAmount = gc.getBalanceManager().getPlayerBalance(p).getAmount();
								if (a > currentPlayerAmount)
									a = currentPlayerAmount;
								gc.getBalanceManager().subtractFromPlayerBalance(p, a);
								sender.sendMessage(ChatColor.GOLD + "" + a + (a == 1 ? " coin" : " coins") + " successfully removed from " + p.getName() + "'s balance.");
							} else {
								sender.sendMessage(Config.ERROR_SUBCOMMAND_INVALID_NUMBER);
							}
						}else {
							sender.sendMessage(Config.ERROR_SUBCOMMAND_PLYAER_IS_NOT_ONLINE);
						}
					}else {
						sender.sendMessage(CommandUtil.getCommandUsage(this.SUBCOMMAND_REMOVE_USAGE_FORMAT, cmd));
					}
				}else {
					sender.sendMessage(Config.ERROR_COMMAND_NO_PERMISSION);
				}
				break;
			default:
				sender.sendMessage(CommandUtil.getCommandUsage(this.COMMAND_USAGE_FORMAT, cmd));
				break;
			}
		}else {
			sender.sendMessage(CommandUtil.getCommandUsage(this.COMMAND_USAGE_FORMAT, cmd));
		}
		return false;
	}

}
