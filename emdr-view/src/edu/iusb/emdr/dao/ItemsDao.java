package edu.iusb.emdr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ItemsDao {
	private Connection conn = null;
	private Statement stmt = null;
	
	public JSONObject getItems() throws Exception {
		JSONObject items = new JSONObject();
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/emdr?"
				+ "user=emdr&password=emdrpasswd");
		stmt = conn.createStatement();
		boolean executed = stmt.execute(
				"SELECT type_id, name FROM emdr.eve_inv_types "
				+ "WHERE marketgroup_id IS NOT NULL "
				+ "ORDER BY type_id");
		if (!executed) {
			stmt.close();
			conn.close();
			return items;			
		}
		
		ResultSet rs = stmt.getResultSet();
		
		while (rs.next()) {
			items.put(rs.getString(1), rs.getString(2));
		}
		rs.close();
		stmt.close();
		conn.close();
		return items;

	}
	
	public JSONArray getItemsMap() throws Exception {
		JSONArray topLevel = new JSONArray(); 
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost/emdr?"
				+ "user=emdr&password=emdrpasswd");
		stmt = conn.createStatement();
		boolean executed = stmt.execute(
				"SELECT type_id, name FROM emdr.eve_inv_types "
				+ "WHERE marketgroup_id IS NOT NULL "
				+ "ORDER BY type_id");
		if (!executed) {
			stmt.close();
			conn.close();
			return topLevel;			
		}
		
		ResultSet rs = stmt.getResultSet();
		
		while (rs.next()) {
			JSONObject node = new JSONObject();
			node.put("id", rs.getString(1));
			node.put("text", rs.getString(2));
			topLevel.add(node);
		}
		rs.close();
		stmt.close();
		conn.close();
		return topLevel;
	}
}
