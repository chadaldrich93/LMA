package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;

import com.st.lma.models.BookLoan;

public class BookLoanJDBC{
	
	private static BookLoanJDBC instance;
	
	private ArrayList<BookLoan> bookLoanList = new ArrayList<BookLoan>();
	
	private BookLoanJDBC() {}
	
	public static BookLoanJDBC getInstance() {
		if (instance == null) {
			instance = new BookLoanJDBC();
		}
		return instance;
	}
	
	public ArrayList<BookLoan> getBookLoanList() {
		return this.bookLoanList;
	}
	
	public void setBookList(ArrayList<BookLoan> bookLoanList) {
		this.bookLoanList = bookLoanList;
	}
	
	public void insertBookLoanList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_book_loans(bookId, branchId, cardNo, dateOut, dueDate)"
            		     + "VALUES (?,?,?,?,?)";
            for (BookLoan b: bookLoanList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, b.getBookId());
                statement.setInt(2, b.getBranchId());
                statement.setInt(3, b.getCardNo());
                ZoneId zid = ZoneId.systemDefault();
                long epochOut = b.getDateOut().atStartOfDay(zid).toEpochSecond();
                long epochDue = b.getDueDate().atStartOfDay(zid).toEpochSecond();
                java.sql.Date sqlDateOut = new java.sql.Date(epochOut);
                java.sql.Date convertedDueDate = new java.sql.Date(epochDue);
                statement.setDate(4, sqlDateOut);
                statement.setDate(5, convertedDueDate);
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
	
	public void loadBookLoanList() {
		bookLoanList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_book_loans";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int bookId = result.getInt("bookId");
            	int branchId = result.getInt("branchId");
            	int cardNo = result.getInt("cardNo");
            	Date dateOut = result.getDate("dateOut");
            	LocalDate localDateOut = dateOut.toLocalDate();
            	Date dueDate = result.getDate("dueDate");
            	LocalDate localDueDate = dueDate.toLocalDate();
            	BookLoan bookLoan = new BookLoan(bookId, branchId, cardNo, localDateOut, localDueDate);
            	bookLoanList.add(bookLoan);
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