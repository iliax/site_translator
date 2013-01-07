package ru.iliax.sitetr;

import java.util.HashMap;
import java.util.Map;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Transaction;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class StaticData {

	private static Key propertiesEntityKey = KeyFactory.createKey(
			"propertiesEntity", "1");

	private static DatastoreService DS = DatastoreServiceFactory
			.getDatastoreService();

	public static String getUrl() {
		try {
			return (String) StaticData.DS.get(StaticData.propertiesEntityKey)
					.getProperties().get("url");
		} catch (EntityNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> getProperties() {
		try {
			Entity props = DS.get(propertiesEntityKey);
			return new HashMap<String, Object>(props.getProperties());
		} catch (EntityNotFoundException e) {
			return new HashMap<String, Object>();
		}
	}

	public static void saveOrUpdateProps(Map<String, Object> props) {
		Entity e = new Entity(propertiesEntityKey);
		for (String key : props.keySet()) {
			e.setProperty(key, props.get(key));
		}
		Transaction tx = DS.beginTransaction();
		DS.put(tx, e);
		tx.commit();
	}

}
