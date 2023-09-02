package dev.mazyar8.gcoin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import dev.mazyar8.gcoin.GCoin;
import dev.mazyar8.gcoin.balance.BalanceManager;

public class JoinEvent extends Event {
	
	private BalanceManager balanceManager = GCoin.getGCoin().getBalanceManager();

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if (!balanceManager.hasBalance(p))
			balanceManager.add(p);
	}
	
}
