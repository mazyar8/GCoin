package dev.mazyar8.gcoin.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dev.mazyar8.gcoin.balance.Balance;
import dev.mazyar8.gcoin.mysql.MySQL;

public class SQLUtils {
	
	public static void add(Balance balance) {
		String query = "INSERT INTO " + MySQL.getTableName() + "(username, amount, record) VALUES (?, ?, ?)";
		try {
			PreparedStatement preparedStatement = MySQL.getPreparedStatement(query);
			preparedStatement.setString(1, balance.getOwner());
			preparedStatement.setString(2, String.valueOf(balance.getAmount()));
			preparedStatement.setString(3, String.valueOf(balance.getRecord()));
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void set(String phrase, String value, String filter) {
		String query = "UPDATE " + MySQL.getTableName() + " SET " + phrase + " = ? " + filter;
		try {
			PreparedStatement preparedStatement = MySQL.getPreparedStatement(query);
			preparedStatement.setString(1, value);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Balance> getAll() {
		List<Balance> balances = new ArrayList<>();
		try {
			ResultSet rs = MySQL.getStatement().executeQuery("SELECT * FROM " + MySQL.getTableName());
			while (rs.next()) {
				String owner = rs.getString("username");
				double amount = Double.parseDouble(rs.getString("amount")), record = Double.parseDouble(rs.getString("record"));
				Balance b = new Balance(owner, amount, record);
				balances.add(b);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return balances;
	}
	
	public static void update(Balance balance) {
		String filter = "WHERE username='" + balance.getOwner() + "'";
		set("amount", String.valueOf(balance.getAmount()), filter);
		set("record", String.valueOf(balance.getRecord()), filter);
	}
	
}
