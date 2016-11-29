package cis232.edu.lab16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;


public class CreateDatabase {
	public static void main(String[] args) throws Exception{
		// Create a named constant for the URL.
		// NOTE: This value is specific for Java DB.
		final String DB_URL = "jdbc:hsqldb:file:StockDB/stock";

		try {
			// Create a connection to the database.
			Connection conn = DriverManager.getConnection(DB_URL);

			// If the DB already exists, drop the tables.
			dropTables(conn);

			// Build the Coffee table.
			buildStockTable(conn);

			// Close the connection.
			conn.close();
		} catch (Exception ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
		
		Scanner keyboard = new Scanner(System.in);
		
		int choice = 0;
		
		InsertDeleteTables tables = new InsertDeleteTables();
		
		while (choice != 5)
		{
			choice = 0;
			
			System.out.println("What would you like to do?\n"
					+ "1) View Table\n"
					+ "2) Insert Stock\n"
					+ "3) Delete Stock\n"
					+ "4) View shares\n"
					+ "5) Quit");
			choice = keyboard.nextInt();
			
			if (choice == 1)
			{
				ViewTable();
			}
			
			if (choice == 2)
			{
				tables.insertTable();
			}
			
			if (choice == 3)
			{
				tables.deleteTable();
			}
			
			if (choice == 4)
			{
				tables.viewShares();
			}
		}
		
		keyboard.close();
	}
	
	public static void dropTables(Connection conn) {
		System.out.println("Checking for existing tables.");

		try {
			// Get a Statement object.
			Statement stmt = conn.createStatement();
			;

			try {
				// Drop the Coffee table.
				stmt.execute("DROP TABLE Stock");
				System.out.println("Stock table dropped.");
			} catch (SQLException ex) {
				// No need to report an error.
				// The table simply did not exist.
			}
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	/**
	 * The buildStockTable method creates the Coffee table and adds some rows
	 * to it.
	 */
	public static void buildStockTable(Connection conn) {
		try {
			// Get a Statement object.
			Statement stmt = conn.createStatement();

			// Create the table.
			stmt.execute("CREATE TABLE Stock (" + "TickerSymbol CHAR(4) NOT NULL PRIMARY KEY, " + "Name CHAR(10), "
					+ "Shares Int, " + "Price Double " + ")");

			// Insert row #1.
			stmt.execute("INSERT INTO Stock VALUES ( " 
					+ "'PEAR', " 
					+ "'Pear', "
					+ "50, "
					+ "111.73 )");

			// Insert row #2.
			stmt.execute("INSERT INTO Stock VALUES ( " 
					+ "'PPSI', " 
					+ "'Pepsi', "
					+ "35, "
					+ "102.26 )");

			// Insert row #3.
			stmt.execute("INSERT INTO Stock VALUES ( " 
					+ "'GOOG', " 
					+ "'Google', "
					+ "20, "
					+ "784.80 )");
						
			// Insert row #4.
			stmt.execute("INSERT INTO Stock VALUES ( " 
					+ "'MSFT', " 
					+ "'Microsoft', "
					+ "35, "
					+ "60.86 )");

			// Insert row #5.
			stmt.execute("INSERT INTO Stock VALUES ( " 
					+ "'WMT', " 
					+ "'Wal-Mart', "
					+ "25, "
					+ "69.37 )");
						
			System.out.println("Stock table created.");
		} catch (SQLException ex) {
			System.out.println("ERROR: " + ex.getMessage());
		}
	}
	
	public static void ViewTable() throws SQLException
	{
		final String DB_URL = "jdbc:hsqldb:file:StockDB/stock";
		
    	String sqlStatement = "SELECT * FROM STOCK ORDER BY TickerSymbol";
    	
    	Connection conn = DriverManager.getConnection(DB_URL);
    	
    	Statement stmt = conn.createStatement();
    	
    	ResultSet result = stmt.executeQuery(sqlStatement);
    	
    	while(result.next()){
    		System.out.println(result.getString("TickerSymbol") + " "
    							+ result.getString("Name") + " "
    							+ result.getInt("Shares") + " "
    							+ result.getDouble("Price"));
    	}
    	
    	conn.close();
	}
}
