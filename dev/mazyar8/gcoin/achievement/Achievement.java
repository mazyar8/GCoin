package dev.mazyar8.gcoin.achievement;

public class Achievement {

	private String name;
	private double amount;
	private String rewardMessage;
	
	public Achievement(String name, double amount, String rewardMessage) {
		this.name = name;
		this.amount = amount;
		this.rewardMessage = rewardMessage;
	}

	public String getName() {
		return name;
	}

	public double getAmount() {
		return amount;
	}

	public String getRewardMessage() {
		return rewardMessage;
	}
	
}
