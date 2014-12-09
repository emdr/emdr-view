package edu.iusb.emdr.analysis;

import java.lang.String;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


public class PriceHistoryDao {
	
		private Connection conn = null;
		private Statement stmt = null;
		
		public Map getOrders() throws Exception{
			return getOrders("34");
		}
		
		public Map getOrders( String itemID) throws Exception {
			Map margins = new HashMap();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/emdr?"
					+ "user=emdr&password=emdrpasswd");
			
			
			String query = "SELECT a.type_id AS itemID, a.price_average-b.price_average AS Slope FROM emdr.items_history a, emdr.items_history b " +
							" WHERE a.type_id = " + itemID + " AND b.type_id = a.type_id " +
							" AND a.date = (SELECT MIN(items_history.date) FROM items_history WHERE type_id = " + itemID + ") " +
							" AND b.date = (SELECT MAX(items_history.date) FROM items_history WHERE type_id = " + itemID + ");";
			
			//System.out.println(query);
			stmt = conn.createStatement();
			boolean executed = stmt.execute(query);
					
			if (!executed) {
				stmt.close();
				conn.close();
				return margins;			
			}
			
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				margins.put(rs.getInt(1), rs.getDouble(2));
			}
			rs.close();
			stmt.close();
			conn.close();
			return margins;
		}
	

}
