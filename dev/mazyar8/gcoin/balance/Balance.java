package dev.mazyar8.gcoin.balance;

public class Balance {

	private String owner;
	private double amount, record;

	public Balance(String owner, double amount, double record) {
		this.owner = owner;
		this.amount = amount;
		this.record = record;
	}
	
	public void add(double amount) {
		this.amount += amount;
		if (this.amount > this.record)
			this.record = this.amount;
	}
	
	public void subtract(double amount) {
		this.amount -= amount;
	}

	public String getOwner() {
		return owner;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getRecord() {
		return record;
	}

	public void setRecord(double record) {
		this.record = record;
	}
	
}
