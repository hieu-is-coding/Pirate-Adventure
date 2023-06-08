package ranking;

import java.sql.*;
import javax.swing.*;

public class Database extends JFrame{
	Connection c = null;
	Statement stmt = null;
	String dbName = "";
	
	public Database(String dbName) {
		try {
			Class.forName("org.sqlite.JDBC");
	        c = DriverManager.getConnection("jdbc:sqlite:PiratesAdventure.db");
	        stmt = c.createStatement();
	        this.dbName = dbName;
	    } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() + " | Select");
	         System.exit(0);
	    }
	    System.out.println("Database selected successfully");
	}
	
	public void createTable() {
		try {
	        String sql = "CREATE TABLE " + dbName + " " +
	                     "(ID       INT PRIMARY KEY     NOT NULL," +
	                     " NAME     STRING    NOT NULL, " + 
	                     " TIME     INT     NOT NULL)";
	        stmt.executeUpdate(sql);
	    } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() + " | Create");
	         System.exit(0);
	    }
	    System.out.println("Table created successfully");
	}
	
	public void insertData(String name, int time) {
		try {
            ResultSet resultSet = stmt.executeQuery("SELECT COUNT(*) FROM " + dbName);
            
            // Retrieve the count from the result set
            int id = resultSet.getInt(1) + 1;
			
			String sql = "INSERT INTO " + dbName + "(ID,NAME,TIME) VALUES (?,?,?)"; 
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setString(2, name);
			ps.setInt(3, time);
			ps.executeUpdate();
			
		} catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() + " | Insert");
	         System.exit(0);
	    }
	    System.out.println("Data inserted successfully");
	}
	
	
	public void deleteData(int id) {
		try {
			String sql = "DELETE from " + dbName + " WHERE ID=" + id;
	        stmt.executeUpdate(sql);
	        
		} catch ( Exception e ) {
	         System.err.println(e.getClass().getName() + ": " + e.getMessage() + " | Delete");
	         System.exit(0);
	    }
	    System.out.println("Data deleted successfully");
	}
	
	public void displayData() {
		int dem = 0;
	    String output = "<html>No:&emsp;Name:&emsp;Score:";
		try {
	        ResultSet rs = stmt.executeQuery( "SELECT * FROM " + dbName + " ORDER BY TIME ASC" );
		      while ( rs.next() || dem <= 10) {
//		         int id = rs.getInt("id");
		    	 dem++;
		         String  name = rs.getString("name");
		         int time  = rs.getInt("time");
		         output = output + "<br>&emsp;&nbsp;" + dem + "&emsp;&emsp;&nbsp;" + name + "&emsp;&emsp;&nbsp;" + time;
				 
		      }
	    } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() + " | Display");
	         System.exit(0);
	    }
		output = output + "</html>";
		JPanel panel = new JPanel();
	    JLabel nameLabel = new JLabel(output);
		panel.add(nameLabel);
		add(panel);
		setTitle("Ultimate Scoreboard");
		setSize(300, 200);
		setVisible(true);
	    System.out.println("Table displayed successfully");
	}
	
//	public static void main(String[] args) {
//		Database db = new Database("Scoreboard");
//		db.createTable();
//		db.insertData("Hieu", 1000);
//		db.deleteData(7);
//		db.displayData();
//	}
}
