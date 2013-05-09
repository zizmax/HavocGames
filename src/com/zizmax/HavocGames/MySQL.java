package com.zizmax.HavocGames;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;

public class MySQL {/*
		  private static Connection conn = null;
		  private static final String hostname = "108.170.40.18";
		  private static final String port = "5532";
		  private static final String dbName = "8310";
		  private static final String DB_URL = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName;
		  private static final String USER = "8310";
		  private static final String PASS = "c56066fb9e";

		  public static void write(String name, int points, int wins, int kills, int deaths, int games){
		      try{
		        if (conn.isClosed()){
		          return;
		        }
		        PreparedStatement stmt = conn.prepareStatement("INSERT INTO HungerGames SET Name='" + 
		          name + "', Points=" + points + ", Wins=" + wins + ", Kills=" + kills + ", Deaths=" + deaths + ", Games=" + games);
		        stmt.executeUpdate();
		      } catch (Exception e){
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		  }

		  public void addGame(String name, int games) {
		      try{
		        if (conn.isClosed()){
		          return;
		        }
		        PreparedStatement stmt = conn.prepareStatement("UPDATE HungerGames SET Games = Games + " + Integer.toString(games) + " WHERE Name = '" + name + "'");
		        stmt.executeUpdate();
		      } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		  }

		  public void addDeath(String name, int deaths) {
		      try{
		        if (conn.isClosed())
		          return;
		        PreparedStatement stmt = conn.prepareStatement("UPDATE HungerGames SET Deaths = Deaths + " + Integer.toString(deaths) + " WHERE Name = '" + name + "'");
		        stmt.executeUpdate();
		        } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      } 
		  }

		  public void addKill(String name, int kills) {
		      try{
		        if (conn.isClosed())
		          return;
		        PreparedStatement stmt = conn.prepareStatement("UPDATE HungerGames SET Kills = Kills + " + Integer.toString(kills) + " WHERE Name = '" + name + "'");
		        stmt.executeUpdate();
		      }
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		  }

		  public void addWin(String name, int wins) {
		      try{
		        if (conn.isClosed())
		          return;
		        PreparedStatement stmt = conn.prepareStatement("UPDATE HungerGames SET Wins = Wins + " + Integer.toString(wins) + " WHERE Name = '" + name + "'");
		        stmt.executeUpdate();
		      } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		  }

		  public void addPoints(String name, int points) {
		      try{
		        if (conn.isClosed())
		          return;
		        PreparedStatement stmt = conn.prepareStatement("UPDATE HungerGames SET Points = Points + " + Integer.toString(points) + " WHERE Name = '" + name + "'");
		        stmt.executeUpdate();
		      } 
		      catch (Exception e){
		        System.out.print(e.getCause()); e.printStackTrace();
		      }

		  }

		  public String get(String name){
		      try{
		        if (conn.isClosed())
		          return null;
		        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM HungerGames");
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next()){
		          if (rs.getString("Name").equals(name)){
		            return name + " " + Integer.toString(rs.getInt("Points")) + " " + Integer.toString(rs.getInt("Wins")) + 
		              " " + Integer.toString(rs.getInt("Kills")) + " " + Integer.toString(rs.getInt("Deaths")) + 
		              " " + Integer.toString(rs.getInt("Games"));
		          }
		        }
		      } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		    return null;
		  }

		  public static String getPoints(String name) {
			  try{
		        if (conn.isClosed()){
		          return null;
		        }
		        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM HungerGames");
		        ResultSet rs = stmt.executeQuery();
		        while (rs.next())
		          if (rs.getString("Name").equals(name))
		            return Integer.toString(rs.getInt("Points"));
		      } 
			  catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		    return null;
		  }

		  public ArrayList<String> leaderboards() {
		    ResultSet rs;
		      try {
		        if (conn.isClosed()){
		          return null;
		        }
		        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM HungerGames");
		        rs = stmt.executeQuery();
		        while (rs.next()){
		        }
		      } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
			return null;
		  }

		  public static void connect() {
		      try{
		        Class.forName("com.mysql.jdbc.Driver");
		        Properties connectionProperties = new Properties();
		        connectionProperties.put("user", USER);
		        connectionProperties.put("password", PASS);
		        connectionProperties.put("autoReconnect", "false");
		        connectionProperties.put("maxReconnects", "0");
		        conn = DriverManager.getConnection(DB_URL, connectionProperties);
		        String sql = "CREATE TABLE IF NOT EXISTS HungerGames (Name CHAR(16), Points INTEGER, Wins INTEGER, Kills INTEGER, Deaths INTEGER, Games INTEGER)";

		        PreparedStatement stmt = conn.prepareStatement(sql);
		        stmt.executeUpdate();
		      } 
		      catch (Exception e) {
		        System.out.print(e.getCause()); e.printStackTrace();
		      }
		    }
		    */
}