package org.bonn.ooka.buchungssystem.ss2022;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DBAccess {
	
	public final static int HOTEL = 0;
	
	public final static int AUTO = 1;

	private String url = "jdbc:postgresql://dumbo.inf.h-brs.de/demouser";
	
	private Connection conn;
	
	public DBAccess() {   
		
	} 

	public static void main(String[] args) {
		DBAccess acc = new DBAccess();
		System.out.println("Mini-Tutorial der Klasse DBAccess" );
		System.out.println("c/o Sascha Alda, 2019 - 2022" );
		System.out.println("---------------------------------" );
		System.out.println("Zunächst MUSS ein externer Client (außerhalb der Komponente!) mit der Methode openConnection() die Session explizit öffnen!" );
		acc.openConnection();
		
		System.out.println("\nSuche nach allen Hotels:" );
		System.out.println("Methodenaufruf: getObjects( DBAccess.HOTEL, \"*\")"   );
		List<String> result = acc.getObjects(DBAccess.HOTEL, "*");
		for ( String str : result ){
			System.out.println( "String: " + str ); 
		}
		
		System.out.println("\nSuche nach Hotels mit dem TeilString \'Jahres\':" );
		System.out.println("Methodenaufruf: getObjects( DBAccess.HOTEL, \"Jahres\")"   );
		result = acc.getObjects(DBAccess.HOTEL, "Jahres");
		for ( String str : result ){
			System.out.println( "String: " + str ); 
		}
		
		System.out.println("\nDann MUSS ein externer Client mit der Methode closeConnection() die Session explizit schließen!" );
		acc.closeConnection();
		// TODO Auto-generated method stub

	}
	
	public void openConnection(){
		  try {
			DriverManager.registerDriver( new org.postgresql.Driver() ); 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  Properties props = new Properties();
		  props.setProperty("user","demouser");
		  props.setProperty("password","demouser");

		  try {
			 this.conn = DriverManager.getConnection(url, props);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public List<String> getObjects( int type, String value  ){
		Statement st;
		ResultSet rs;
		List<String> result = new ArrayList();
		if (value.equals("*") ) {
			value = "";
		}
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM buchungsystem.hotel WHERE buchungsystem.hotel.name ilike " + "\'%" + value +  "%\'" );
			while (rs.next() ){
				    // System.out.println( "Hotel: " + rs.getString( "name" ) ); 
				    result.add( rs.getString( 1 ) );
				    result.add( rs.getString( 2 ) );
				    result.add( rs.getString( 3 ) );
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public void closeConnection(){
		   try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 

	}


}
