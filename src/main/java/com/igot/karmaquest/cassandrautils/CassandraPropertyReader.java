package com.igot.karmaquest.cassandrautils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class CassandraPropertyReader {

	private final Properties properties = new Properties();
	  private static final String file = "cassandratablecolumn.properties";
	  private static CassandraPropertyReader cassandraPropertyReader = null;

	  private CassandraPropertyReader() throws IOException {
	    InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
	    try {
	      properties.load(in);
	    } catch (IOException e) {
	    	throw e;
	    }
	  }

	  public static CassandraPropertyReader getInstance() {
	    if (null == cassandraPropertyReader) {
	      synchronized (CassandraPropertyReader.class) {
	        if (null == cassandraPropertyReader) {
	          try {
				cassandraPropertyReader = new CassandraPropertyReader();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        }
	      }
	    }
	    return cassandraPropertyReader;
	  }

	  public String readProperty(String key) {
	    return properties.getProperty(key) != null ? properties.getProperty(key) : key;
	  }
}
