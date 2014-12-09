package edu.iusb.emdr.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ItemsDaoTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetItems() {
		ItemsDao data = new ItemsDao();
		JSONObject items = null;
		try {
			items = data.getItems();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String firstItem = null;
		
		assertTrue(items.get("0") instanceof String);
		if (items.get("0") instanceof String)
			firstItem = (String)items.get("0");
		
		assertFalse("item list empty", items.isEmpty());
		assertFalse("empty string", firstItem.isEmpty());
	}
	
	@Test
	public void testGetItemsMap() {
		ItemsDao data = new ItemsDao();
		JSONArray items = null;
		try {
			items = data.getItemsMap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONObject firstKey = null;
		
		assertTrue(items.get(0) instanceof JSONObject);
		if (items.get(0) instanceof JSONObject)
			firstKey = (JSONObject)items.get(0);
		
		assertFalse("item list empty", items.isEmpty());
		assertFalse("empty string", firstKey.isEmpty());		
	}

}
