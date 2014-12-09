package edu.iusb.emdr.dao;

import java.util.HashMap;

public class GroupMap<K, V> extends HashMap<String, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8137652334087097141L;

	public GroupMap() {
		super();
		put("id", "");
		put("text", "");
	}
	
}
