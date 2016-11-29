package cis232.edu.lab16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class InsertDeleteTables 
{
	public InsertDeleteTables()
	{
		
	}
	
	public void insertTable() throws SQLException
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Give me a Ticker Symbol:");
		String ticker = keyboard.nextLine();
		System.out.println("Give me a Company Name:");
		String name = keyboard.nextLine();
		System.out.println("Give me the number of shares:");
		String shares = keyboard.nextLine();
		System.out.println("Give me the Price:");
		String price = keyboard.nextLine();
		
		final String DB_URL = "jdbc:hsqldb:file:StockDB/stock";
		
		Connection conn = DriverManager.getConnection(DB_URL);
		
	
		String sql = "INSERT INTO STOCK"
					 + " (TickerSymbol,Name,Shares,Price)"
					 + " VALUES "
					 + " (?, ?, ?, ?);";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.setString(1, ticker);
		stmt.setString(2, name);
		stmt.setDouble(3, Integer.parseInt(shares));
		stmt.setDouble(4, Double.parseDouble(price));
		
		stmt.executeUpdate();
		
		conn.close();
		
		System.out.println("Inserted Stock!");
	}
	
	public void deleteTable() throws SQLException
	{
		Scanner keyboard = new Scanner(System.in);
		System.out.println("Insert a Ticker Symbol:");
		String ticker = keyboard.nextLine();
		
		final String DB_URL = "jdbc:hsqldb:file:StockDB/stock";
		
		Connection conn = DriverManager.getConnection(DB_URL);
		
	
		String sql = "DELETE FROM STOCK WHERE TickerSymbol =  '" + ticker + "';";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		stmt.executeUpdate();
		
		conn.close();
		
		System.out.println("Stock Deleted");
	}
	
	public void viewShares() throws SQLException
	{
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Please enter the ticker symbol: ");
		String ticker = keyboard.nextLine();
		
		System.out.println("Please enter the current price: ");
		double currentPrice = keyboard.nextDouble();
		
		final String DB_URL = "jdbc:hsqldb:file:StockDB/stock";
		
		Connection conn = DriverManager.getConnection(DB_URL);
	
		String sql = "SELECT * FROM STOCK where TickerSymbol = '" + ticker + "';";
		
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		
		ResultSet result = stmt.executeQuery();
		int shares = 0;
		double price = 0;
		
		if (result.next()){
			shares = result.getInt("Shares");
			price = result.getDouble("Price");
    	}
		conn.close();
		
		double profit = (shares * currentPrice) - (shares * price);
		
		System.out.printf("You have made %.2f in profits\n\n", profit);
	}
}
