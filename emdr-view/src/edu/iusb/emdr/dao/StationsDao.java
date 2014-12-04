package edu.iusb.emdr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StationsDao {
	private Connection conn = null;
	private Statement stmt = null;
	
	public List<String> getStations() throws Exception {
		ArrayList<String> items = new ArrayList<String>();
		
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/emdr?"
				+ "user=emdr&password=emdrpasswd");
		stmt = conn.createStatement();
		boolean executed = stmt.execute(
				"SELECT solarsystem_name FROM emdr.eve_sta_stations WHERE station_id IN "
				+ "('60003760' /*Jita*/,"
				+ "'60008494' /*Amarr*/,"
				+ "'60004588' /*Rens*/,"
				+ "'60011866' /*Dodixie*/) "
				+ "ORDER BY solarsystem_name "
				+ ";"
				);
		if (!executed) {
			stmt.close();
			conn.close();
			return items;			
		}
		
		ResultSet rs = stmt.getResultSet();
		
		while (rs.next()) {
			items.add(rs.getString(1));
		}
		rs.close();
		stmt.close();
		conn.close();
		return items;
	}
}
