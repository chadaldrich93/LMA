package com.st.lma.main;

import com.st.lma.models.BookLoan;

public class BorrowerService{
	
	private static BorrowerService instance;
	
	private BorrowerService() {}
	
	public static BorrowerService getInstance() {
		if (instance == null) {
			instance = new BorrowerService();
		}
		return instance;
	}
	
	public void addBookLoan(BookLoan newLoan) {
		
	}
	
	public void deleteBookLoan(int bookId, int branchId, int cardNo) {
		
	}
}