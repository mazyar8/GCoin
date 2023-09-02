package dev.mazyar8.gcoin;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

import dev.mazyar8.gcoin.achievement.AchievementManager;
import dev.mazyar8.gcoin.balance.BalanceManager;
import dev.mazyar8.gcoin.command.GCoinCommand;
import dev.mazyar8.gcoin.command.PayCommand;
import dev.mazyar8.gcoin.config.Config;
import dev.mazyar8.gcoin.event.Event;
import dev.mazyar8.gcoin.event.JoinEvent;
import dev.mazyar8.gcoin.mysql.MySQL;
import dev.mazyar8.gcoin.permissions.Permissions;
import dev.mazyar8.gcoin.util.SQLUtils;

public class GCoin extends JavaPlugin {
	
	private AchievementManager achievementManager;
	private BalanceManager balanceManager;
	private Config config;
	private MySQL mySQL;
	
	private static GCoin gc;

	@Override
	public void onEnable() {
		init();
		load();
		mySQL.connect();
		balanceManager.addAll(SQLUtils.getAll());
		addCommand("gcoin", new GCoinCommand());
		addCommand("pay", new PayCommand());
		addEvent(new JoinEvent());
		addPermission(Permissions.COMMAND_GCOIN_ADD);
		addPermission(Permissions.COMMAND_GCOIN_BALANCE);
		addPermission(Permissions.COMMAND_GCOIN_RELOAD);
		addPermission(Permissions.COMMAND_GCOIN_REMOVE);
		addPermission(Permissions.COMMAND_PAY);
	}
	
	@Override
	public void onDisable() {
		mySQL.disconnect();
	}
	
	/** init fields */
	public void init() {
		gc = this;
		achievementManager = new AchievementManager();
		balanceManager = new BalanceManager();
		config = new Config(getConfig());
		mySQL = new MySQL(Config.DATABASE_HOST, Config.DATABASE_NAME, Config.DATABASE_USER, Config.DATABASE_PASS, Config.DATABASE_PORT);
	}
	
	public void load() {
		achievementManager.load(getConfig());
	}
	
	public void save() {
		achievementManager.save(getConfig());
		saveConfig();
	}
	
	public void reload() {
		reloadConfig();
		config.reload(gc.getConfig());
		achievementManager.reload(gc.getConfig());
	}
	
	/** get new plugin command */
	public void addCommand(String cmd, CommandExecutor executor) {
		Bukkit.getPluginCommand(cmd).setExecutor(executor);
	}
	
	/** register new listener */
	public void addEvent(Event e) {
		Bukkit.getPluginManager().registerEvents(e, this);
	}
	
	/** add new permission */
	public void addPermission(Permission permission) {
		Bukkit.getPluginManager().addPermission(permission);
	}
	
	public BalanceManager getBalanceManager() {
		return balanceManager;
	}

	public MySQL getMySQL() {
		return mySQL;
	}

	public static GCoin getGCoin() {
		return gc;
	}
	
}
