package dev.mazyar8.gcoin.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.ChatColor;

import dev.mazyar8.gcoin.util.Logger;

public class MySQL {

	private String host, databaseName, username, password;
	private int port;
	private static String tableName = "balances";
	
	private static Connection connection;
	
	public MySQL(String host, String databaseName, String username, String password, int port) {
		this.host = host;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.port = port;
	}
	
	public void connect() {
		try {
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port, username, password);
			createDatabaseIfNotExists();
			connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + databaseName, username, password);
			Logger.log(ChatColor.GREEN + "successfully connected to database");
			createTableIfNotExists();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void disconnect() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/** create database if not exist */
	private void createDatabaseIfNotExists() throws SQLException {
		if (getStatement().executeUpdate("CREATE DATABASE IF NOT EXISTS " + databaseName) == 1)
			Logger.log(ChatColor.GREEN + "database created successfully");
	}
	
	/** create table if not exist */
	private void createTableIfNotExists() throws SQLException {
		getStatement().execute("CREATE TABLE IF NOT EXISTS " + tableName + " (username VARCHAR(16), amount VARCHAR(24), record VARCHAR(24))");
	}

	public static String getTableName() {
		return tableName;
	}

	public static Connection getConnection() {
		return connection;
	}
	
	/** return new statement */
	public static Statement getStatement() throws SQLException {
		return connection.createStatement();
	}
	
}
