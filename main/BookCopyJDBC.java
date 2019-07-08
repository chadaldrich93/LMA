package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.BookCopy;

public class BookCopyJDBC{
	
	private static BookCopyJDBC instance;
	
	private ArrayList<BookCopy> bookCopyList = new ArrayList<BookCopy>();
	
	private BookCopyJDBC() {}
	
	public static BookCopyJDBC getInstance() {
		if (instance == null) {
			instance = new BookCopyJDBC();
		}
		return instance;
	}
	
	public ArrayList<BookCopy> getBookCopyList() {
		return this.bookCopyList;
	}
	
	public void setBookCopyList(ArrayList<BookCopy> bookCopyList) {
		this.bookCopyList = bookCopyList;
	}
	
	public void insertBookCopyList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_book_copies(bookId, branchId, noOfCopies)"
            		     + "VALUES (?,?,?,?)";
            for (BookCopy b: bookCopyList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, b.getBookId());
                statement.setInt(2, b.getBranchId());
                statement.setInt(3, b.getNoOfCopies());
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
	
	public void loadBookCopyList() {
		bookCopyList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_book_copies";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int bookId = result.getInt("bookId");
            	int branchId = result.getInt("branchId");
            	int noOfCopies = result.getInt("noOfCopies");
            	BookCopy bookCopy = new BookCopy(bookId, branchId, noOfCopies);
            	bookCopyList.add(bookCopy);
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