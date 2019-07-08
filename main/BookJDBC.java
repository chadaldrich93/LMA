package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.Book;

public class BookJDBC{
	
	private static BookJDBC instance;
	
	private ArrayList<Book> bookList = new ArrayList<Book>();
	
	private BookJDBC() {}
	
	public static BookJDBC getInstance() {
		if (instance == null) {
			instance = new BookJDBC();
		}
		return instance;
	}
	
	public ArrayList<Book> getBookList() {
		return this.bookList;
	}
	
	public void setBookList(ArrayList<Book> bookList) {
		this.bookList = bookList;
	}
	
	public void insertBookList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_book(bookId, title, authId, pubId)"
            		     + "VALUES (?,?,?,?)";
            for (Book b: bookList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, b.getBookId());
                statement.setString(2, b.getBookTitle());
                statement.setInt(3, b.getAuthId());
                statement.setInt(4, b.getPubId());
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
	
	public void loadBookList() {
		bookList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_book";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int bookId = result.getInt("bookId");
            	String title = result.getString("title");
            	int authId = result.getInt("authId");
            	int pubId = result.getInt("pubId");
            	Book book = new Book(bookId, title, authId, pubId);
            	bookList.add(book);
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