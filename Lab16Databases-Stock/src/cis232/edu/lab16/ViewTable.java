package cis232.edu.lab16;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ViewTable 
{
	public ViewTable() throws SQLException
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
