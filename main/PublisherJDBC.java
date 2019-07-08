package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.Publisher;

public class PublisherJDBC{
	
	private static PublisherJDBC instance;
	
	private ArrayList<Publisher> publisherList = new ArrayList<Publisher>();
	
	private PublisherJDBC() {}
	
	public static PublisherJDBC getInstance() {
		if (instance == null) {
			instance = new PublisherJDBC();
		}
		return instance;
	}
	
	public ArrayList<Publisher> getPublisherList() {
		return this.publisherList;
	}
	
	public void setPublisherList(ArrayList<Publisher> publisherList) {
		this.publisherList = publisherList;
	}
	
	public void insertPublisherList() {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_publisher(publisherId, publisherName, publisherAddress," +
            		                                        "publisherPhone)" + "VALUES (?,?,?,?)";
            for (Publisher p: publisherList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, p.getPublisherId());
                statement.setString(2, p.getPublisherName());
                statement.setString(3, p.getPublisherAddress());
                statement.setString(4, p.getPublisherPhone());
                statement.executeUpdate();
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (result != null) {
					result.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void loadPublisherList() {
		publisherList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_publisher";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int publisherId = result.getInt("publisherId");
            	String publisherName = result.getString("publisherName");
            	String publisherAddress = result.getString("publisherAddress");
            	String publisherPhone = result.getString("publisherPhone");
            	Publisher publisher = new Publisher(publisherId, publisherName, publisherAddress, publisherPhone);
            	publisherList.add(publisher);
            }
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (result != null) {
					result.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (conn != null) {
					conn.close();
				}
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}