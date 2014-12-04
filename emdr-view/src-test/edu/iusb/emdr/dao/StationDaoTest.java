package edu.iusb.emdr.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StationDaoTest {

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
	public void testGetStations() {
		StationsDao stationData = new StationsDao();
		List<String> stations = null;
		try {
			stations = stationData.getStations();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertFalse("Stations empty.", stations.isEmpty());
		assertEquals("Station size is not 4.", 4, stations.size());
		assertEquals("First station not Amarr.", "Amarr", stations.get(0));
	}

}
