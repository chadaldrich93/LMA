package com.st.lma.main;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.st.lma.models.LibraryBranch;

public class LibraryBranchJDBC{
	
	private static LibraryBranchJDBC instance;
	
	private ArrayList<LibraryBranch> libraryBranchList = new ArrayList<LibraryBranch>();
	
	private LibraryBranchJDBC() {}
	
	public static LibraryBranchJDBC getInstance() {
		if (instance == null) {
			instance = new LibraryBranchJDBC();
		}
		return instance;
	}
	
	public ArrayList<LibraryBranch> getLibraryBranchList() {
		return this.libraryBranchList;
	}
	
	public void setLibraryBranchList(ArrayList<LibraryBranch> libraryBranchList) {
		this.libraryBranchList = libraryBranchList;
	}
	
	public void insertLibraryBranchList() {;
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "INSERT INTO library.tbl_library_branch(branchId, branchName, branchAddress)"
            		     + "VALUES (?,?,?)";
            for (LibraryBranch b: libraryBranchList) {
                statement = conn.prepareStatement(sql);
                statement.setInt(1, b.getBranchId());
                statement.setString(2, b.getBranchName());
                statement.setString(3, b.getBranchAddress());
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
	
	public void loadLibraryBranchList() {
		libraryBranchList.clear();
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet result = null;
		try {
			conn = (Connection) DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC", "root", "root");
            String sql = "SELECT * FROM library.tbl_library_branch";
            statement = conn.prepareStatement(sql);
            result = statement.executeQuery();
            while (result.next()) {
            	int branchId = result.getInt("branchId");
            	String branchName = result.getString("branchName");
            	String branchAddress = result.getString("branchAddress");
            	LibraryBranch branch = new LibraryBranch(branchId, branchName, branchAddress);
            	libraryBranchList.add(branch);
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