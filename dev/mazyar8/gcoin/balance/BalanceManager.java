package dev.mazyar8.gcoin.balance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;

import dev.mazyar8.gcoin.achievement.Achievement;
import dev.mazyar8.gcoin.achievement.AchievementManager;
import dev.mazyar8.gcoin.config.Config;
import dev.mazyar8.gcoin.util.NumberUtil;
import dev.mazyar8.gcoin.util.SQLUtils;

public class BalanceManager {

	private List<Balance> balances = new ArrayList<>();
	
	public Balance getPlayerBalance(Player player) {
		for (Balance b : balances)
			if (b.getOwner().equals(player.getName()))
				return b;
		return null;
	}
	
	public void addToPlayerBalance(Player player, double amount) {
		Balance balance = getPlayerBalance(player);
		double lastRecord = balance.getRecord();
		balance.add(amount);
		SQLUtils.update(balance);
		for (Achievement a : AchievementManager.getAchievements().stream().filter(achievement -> achievement.getAmount() > lastRecord && achievement.getAmount() <= balance.getAmount()).collect(Collectors.toList()))
			player.sendMessage(a.getRewardMessage().replace("{player}", player.getName()));
	}
	
	public void subtractFromPlayerBalance(Player player, double amount) {
		Balance balance = getPlayerBalance(player);
		balance.subtract(amount);
		SQLUtils.update(balance);
	}
	
	public boolean hasBalance(Player player) {
		return getPlayerBalance(player) != null;
	}
	
	public void add(Player player) {
		double a = Config.DEFAULT_BALANCE_AMOUNT;
		Balance b = new Balance(player.getName(), a, a);
		balances.add(b);
		SQLUtils.add(b);
	}
	
	public void addAll(List<Balance> balances) {
		for (Balance b : balances)
			this.balances.add(b);
	}
	
	public double getPlayerMaxBalance(Player player) {
		for (PermissionAttachmentInfo p : player.getEffectivePermissions()) {
			String permission = p.getPermission();
			if (permission.startsWith("gcoin.max.")) {
				String[] args = permission.replace(".", " ").split(" ");
				if (args.length > 2 && NumberUtil.isDouble(args[2]))
					return Double.parseDouble(args[2]);
			}
		}
		return Config.DEFAULT_BALANCE_MAX;
	}
	
}
