package edu.iusb.emdr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class ItemsDao {
	private Connection conn = null;
	private Statement stmt = null;
	Map<Integer, List<Integer>> childrenMap = new HashMap<Integer, List<Integer>>();
	Map<Integer, List<Object>> itemsMap = new HashMap<Integer, List<Object>>();
	Map<Integer, List<Integer>> groupedItemsMap = new HashMap<Integer, List<Integer>>();
	
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
		boolean executed = false;
		ResultSet rs = null;
		Map<Integer, List<Object>> groupMap = new HashMap<Integer, List<Object>>();
		
		executed = stmt.execute(
				"SELECT type_id, name, marketgroup_id FROM emdr.eve_inv_types "
				+ "WHERE marketgroup_id IS NOT NULL "
				+ "ORDER BY type_id");
		if (!executed) {
			stmt.close();
			conn.close();
			return topLevel;			
		}
		
		rs = stmt.getResultSet();
		
		
		while (rs.next()) {
			List<Object> typeProp = new ArrayList<Object>();
			typeProp.add(rs.getInt(1));		// type
			typeProp.add(rs.getString(2));	// name
			typeProp.add(rs.getInt(3));		// group
			itemsMap.put(rs.getInt(1), typeProp);
			
			List<Integer> group = groupedItemsMap.get(rs.getInt(3));
			if (group == null)
				group = new ArrayList<Integer>();
			group.add(rs.getInt(1));
			groupedItemsMap.put(rs.getInt(3), group);
		}
		
		executed = stmt.execute(
				"SELECT marketgroup_id, marketgroup_name, parentgroup_id, "
				+ "has_types "
				+ "FROM emdr.eve_inv_marketgroups ORDER BY marketgroup_id;");
		if (!executed) {
			stmt.close();
			conn.close();
			return topLevel;			
		}
		
		rs = stmt.getResultSet();
		
		while (rs.next()) {
			List<Object> groupProp = new ArrayList<Object>();
			groupProp.add(rs.getInt(1));
			groupProp.add(rs.getString(2));
			groupProp.add(rs.getInt(3));
			groupMap.put(rs.getInt(1), groupProp);
			
			List<Integer> children = childrenMap.get(rs.getInt(3));
			if (children == null)
				children = new ArrayList<Integer>();
			children.add(rs.getInt(1));
			childrenMap.put(rs.getInt(3), children);
		}
		
		for (Integer nodeId : childrenMap.get(0)) {
			JSONObject node = new JSONObject();
			node.put("id", nodeId);
			node.put("text", (String)groupMap.get(nodeId).get(1));
			JSONArray children = buildCatTree(groupMap, (List<Integer>) childrenMap.get(nodeId), nodeId);
			
			if (groupedItemsMap.get(nodeId) != null) {
				for(int groupedItems : groupedItemsMap.get(nodeId)) {
					JSONObject item = new JSONObject();
					item.put("id", itemsMap.get(groupedItems).get(0));
					item.put("text", itemsMap.get(groupedItems).get(1));
					children.add(item);
				}
			}
			node.put("children", children);
			topLevel.add(node);
		}
		
		if (true)
			return topLevel;
		
		Iterator<Integer> it = groupMap.keySet().iterator();
		while(it.hasNext()) {
		}

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
	
	private JSONArray buildCatTree(Map<Integer, List<Object>> tree, List<Integer> children, int nodeId) {
		JSONArray groups = new JSONArray();
		for (Integer childId : children) {
			JSONObject node = new JSONObject();
			node.put("id", childId);
			node.put("text", (String)tree.get(childId).get(1));
			if ((List<Integer>) childrenMap.get(childId) != null)
				node.put("children", buildCatTree(tree, (List<Integer>) childrenMap.get(childId), childId));
			groups.add(node);

			if (groupedItemsMap.get(nodeId) != null) {
				for(int groupedItems : groupedItemsMap.get(nodeId)) {
					JSONObject item = new JSONObject();
					item.put("id", itemsMap.get(groupedItems).get(0));
					item.put("text", itemsMap.get(groupedItems).get(1));
					groups.add(item);
				}
			}
		}
		
		return groups;
	}
}
