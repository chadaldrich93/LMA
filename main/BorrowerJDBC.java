package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.Borrower;

public class BorrowerJDBC{
	
	private static BorrowerJDBC instance;
	
	private ArrayList<Borrower> borrowerList = new ArrayList<Borrower>();
	
	private BorrowerJDBC() {}
	
	public static BorrowerJDBC getInstance() {
		if (instance == null) {
			instance = new BorrowerJDBC();
		}
		return instance;
	}
	
	public ArrayList<Borrower> getBorrowerList() {
		return this.borrowerList;
	}
	
	public void setBookList(ArrayList<Borrower> borrowerList) {
		this.borrowerList = borrowerList;
	}
	
	public void insertBorrowerList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_borrower(cardNo, name, address, phone)"
            		     + "VALUES (?,?,?,?)";
            for (Borrower b: borrowerList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, b.getCardNo());
                statement.setString(2, b.getName());
                statement.setString(3, b.getAddress());
                statement.setString(4, b.getAddress());
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
	
	public void loadBorrowerList() {
		borrowerList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_borrower";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int cardNo = result.getInt("cardNo");
            	String name = result.getString("name");
            	String address = result.getString("address");
            	String phone = result.getString("phone");
            	Borrower borrower = new Borrower(cardNo, name, address, phone);
            	borrowerList.add(borrower);
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