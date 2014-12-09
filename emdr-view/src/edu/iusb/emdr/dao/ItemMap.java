package edu.iusb.emdr.dao;

import java.util.Map;

import org.json.simple.JSONObject;

public class ItemMap<K, V> extends JSONObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1087218078306610475L;

	@SuppressWarnings("unchecked")
	public ItemMap(String sid, String stext) {
		super();
		put("id", sid);
		put("text", stext);
	}

	@SuppressWarnings("rawtypes")
	public ItemMap(Map map) {
		super(map);
		// TODO Auto-generated constructor stub
	}


}
