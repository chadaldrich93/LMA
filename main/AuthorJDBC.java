package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.Author;

public class AuthorJDBC{
	
	private static AuthorJDBC instance;
	
	private ArrayList<Author> authorList = new ArrayList<Author>();
	
	private AuthorJDBC() {}
	
	public static AuthorJDBC getInstance() {
		if (instance == null) {
			instance = new AuthorJDBC();
		}
		return instance;
	}
	
	public ArrayList<Author> getAuthorList() {
		return this.authorList;
	}
	
	public void setAuthorList(ArrayList<Author> authorList) {
		this.authorList = authorList;
	}
	
	public void insertAuthorList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_author(authorId, authorName)"
            		     + "VALUES (?,?)";
            for (Author a: authorList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, a.getAuthorId());
                statement.setString(2, a.getAuthorName());
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
	
	public void loadAuthorList() {
		authorList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_author";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int authorId = result.getInt("authorId");
            	String authorName = result.getString("authorName");
            	Author author = new Author(authorId, authorName);
            	authorList.add(author);
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