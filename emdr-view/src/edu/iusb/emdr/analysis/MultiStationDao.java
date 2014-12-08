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


public class MultiStationDao {
	
		private Connection conn = null;
		private Statement stmt = null;
		
		public Map getOrders() throws Exception{
			return getOrders("60003760");
		}
		
		public Map getOrders( String stationID) throws Exception {
			Map margins = new HashMap();
			
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/emdr?"
					+ "user=emdr&password=emdrpasswd");
			
			
			String query = "SELECT items_selling.type_id AS SellID,"+
							" MIN(items_selling.price) - MAX(items_buying.price) AS Margin"+
							" FROM items_selling"+
							" JOIN items_buying ON items_selling.type_id = items_buying.type_id AND items_selling.station_id = items_buying.station_id"+
							" JOIN items_history ON items_selling.type_id = items_history.type_id" +
							" WHERE items_selling.station_id = " + stationID + " AND items_history.quantity > 200 AND items_history.date = '2014-09-09'"+
							" GROUP BY items_selling.type_id ORDER BY Margin DESC LIMIT 10;";
			
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
