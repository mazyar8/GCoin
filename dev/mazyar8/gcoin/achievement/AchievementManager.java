package dev.mazyar8.gcoin.achievement;

import java.util.concurrent.CopyOnWriteArrayList;

import org.bukkit.configuration.file.FileConfiguration;

import dev.mazyar8.gcoin.util.NumberUtil;

public class AchievementManager {

	private static CopyOnWriteArrayList<Achievement> achievements = new CopyOnWriteArrayList<>();
	
	public void load(FileConfiguration fc) {
		for (String s : fc.getKeys(true)) {
			String[] args = s.replace(".", " ").split(" ");
			if (args.length == 2) {
				String amountPath = s.concat(".amount"), rewardMessagePath = s.concat(".reward.message");
				if (fc.isSet(amountPath) && fc.isSet(rewardMessagePath))
					if (NumberUtil.isDouble(fc.getString(amountPath)))
						achievements.add(new Achievement(args[1], Double.parseDouble(fc.getString(amountPath)), fc.getString(rewardMessagePath)));
			}
		}
	}
	
	public void save(FileConfiguration fc) {
		for (Achievement a : achievements) {
			fc.set("achievement." + a.getName() + ".amount", a.getAmount());
			fc.set("achievement." + a.getName() + ".reward.message", a.getRewardMessage());
		}
	}
	
	public void reload(FileConfiguration fc) {
		achievements.clear();
		load(fc);
	}

	public static CopyOnWriteArrayList<Achievement> getAchievements() {
		return achievements;
	}
	
}
